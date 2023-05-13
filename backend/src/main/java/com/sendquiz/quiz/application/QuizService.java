package com.sendquiz.quiz.application;

import com.sendquiz.email.application.EmailQuizSender;
import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.quiz.domain.Quiz;
import com.sendquiz.quiz.presentation.request.QuizSave;
import com.sendquiz.quiz.repository.QuizRepository;
import com.sendquiz.quiz_filter.application.request.QuizFilterSearch;
import com.sendquiz.quiz_filter.repository.QuizFilterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sendquiz.global.constant.CommonConstant.*;
import static java.util.Objects.*;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizFilterRepository quizFilterRepository;
    private final EmailQuizSender emailQuizSender;
    private final CacheManager cacheManager;

    public void sendRandomQuizList() {
        List<QuizFilterSearch> quizFilterSearchList = quizFilterRepository.findAllQuizFilterSearch();
        for (QuizFilterSearch quizFilterSearch : quizFilterSearchList) {
            List<Quiz> filteredQuizList = getFilteredQuizList(quizFilterSearch);

            Collections.shuffle(filteredQuizList);
            List<Quiz> randomQuizList = filteredQuizList.stream()
                    .limit(quizFilterSearch.getNumOfProblem())
                    .toList();

            emailQuizSender.sendQuizList(randomQuizList, quizFilterSearch.getEmail());
        }
    }

    protected List<Quiz> getFilteredQuizList(QuizFilterSearch quizFilterSearch) {
        return new ArrayList<>(findAll()
                .stream()
                .filter(q -> q.filterQuizList(quizFilterSearch))
                .toList());
    }

    @Cacheable(value = QUIZ_CACHE)
    public List<Quiz> findAll() {
        Cache cache = requireNonNull(cacheManager.getCache(QUIZ_CACHE));
        List<Quiz> quizList = cache.get(QUIZ_LIST, List.class);

        if (quizList == null) {
            quizList = quizRepository.findAll();
            cache.put(QUIZ_LIST, quizList);
        }

        return quizList;
    }

    @Scheduled(fixedRate = ONE_DAY)
    @CacheEvict(value = QUIZ_CACHE, allEntries = true)
    public void flushCacheToDB() {
        log.info("flushCacheToDB");
    }

    // todo 성능 테스트로 비교해보기
//    public void sendRandomQuizList() {
//        List<QuizFilterSearch> quizFilterSearchList = quizFilterRepository.findAllQuizFilterSearch();
//        for (QuizFilterSearch quizFilterSearch : quizFilterSearchList) {
//            List<Quiz> randomQuizList = quizRepository.findRandomQuizList(quizFilterSearch);
//            emailQuizSenderProd.sendQuizList(randomQuizList, quizFilterSearch.getEmail());
//        }
//    }

    @Transactional
    public void save(QuizSave quizSave) {
        Subject subject = Subject.fromValue(quizSave.getSubject());
        quizRepository.save(quizSave.toEntity(subject));
    }
}
