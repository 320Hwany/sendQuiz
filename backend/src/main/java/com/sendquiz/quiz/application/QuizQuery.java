package com.sendquiz.quiz.application;

import com.sendquiz.email.application.EmailQuizSender;
import com.sendquiz.quiz.domain.Quiz;
import com.sendquiz.quiz.dto.request.QuizSearch;
import com.sendquiz.quiz.dto.response.QuizPagingResponse;
import com.sendquiz.quiz.dto.response.QuizSearchResponse;
import com.sendquiz.quiz.repository.QuizRepository;
import com.sendquiz.quiz_filter.dto.request.QuizFilterSearch;
import com.sendquiz.quiz_filter.repository.QuizFilterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static com.sendquiz.global.constant.CommonConstant.QUIZ_CACHE;
import static com.sendquiz.global.constant.CommonConstant.QUIZ_LIST;
import static com.sendquiz.quiz.dto.response.QuizPagingResponse.toQuizPagingResponse;
import static java.util.Objects.requireNonNull;


@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class QuizQuery {

    private final QuizFilterRepository quizFilterRepository;
    private final EmailQuizSender emailQuizSender;
    private final CacheManager cacheManager;
    private final QuizRepository quizRepository;

    @Async
    public void sendRandomQuizzes() {
        List<QuizFilterSearch> quizFilterSearchList = quizFilterRepository.findAllQuizFilterSearch();

        List<CompletableFuture<Void>> emailSendingFutures = quizFilterSearchList.stream()
                .map(quizFilterSearch -> CompletableFuture.runAsync(() -> {
                    List<Quiz> filteredQuizzes = getFilteredQuizzes(quizFilterSearch);

                    Collections.shuffle(filteredQuizzes);
                    List<Quiz> randomQuizzes = filteredQuizzes.stream()
                            .limit(quizFilterSearch.getNumOfProblem())
                            .toList();

                    emailQuizSender.sendQuizzes(randomQuizzes, quizFilterSearch.getEmail());
                }))
                .toList();

        CompletableFuture.allOf(emailSendingFutures.toArray(new CompletableFuture[0])).join();
    }

    protected List<Quiz> getFilteredQuizzes(QuizFilterSearch quizFilterSearch) {
        return new ArrayList<>(findAll()
                .stream()
                .filter(q -> q.filterQuiz(quizFilterSearch))
                .toList());
    }

    @Cacheable(value = QUIZ_CACHE)
    public List<Quiz> findAll() {
        Cache cache = requireNonNull(cacheManager.getCache(QUIZ_CACHE));
        List<Quiz> quizList = cache.get(QUIZ_LIST, List.class);

        if (quizList == null) {
            quizList = quizRepository.findAll();
            cache.put(QUIZ_LIST, quizList);
            log.info("Get Quiz From Database");
        }

        return quizList;
    }

    public List<QuizPagingResponse> findAllWithFilter(QuizSearch quizSearch) {
        List<Quiz> quizzes = findAll();
        int startIndex = (quizSearch.getPage() - 1) * 10;
        List<QuizPagingResponse> pagingQuizzes = new ArrayList<>();

        List<QuizSearchResponse> filterQuizzes = quizzes.stream()
                .filter(quiz -> quiz.filterQuiz(quizSearch))
                .map(QuizSearchResponse::toQuizSearchResponse)
                .toList();

        IntStream.range(startIndex, filterQuizzes.size())
                .mapToObj(i -> toQuizPagingResponse(filterQuizzes.get(i), i))
                .limit(10)
                .forEach(pagingQuizzes::add);

        return pagingQuizzes;
    }
}
