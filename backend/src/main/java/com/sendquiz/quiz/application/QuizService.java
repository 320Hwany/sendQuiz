package com.sendquiz.quiz.application;

import com.sendquiz.email.application.prod.EmailQuizSenderProd;
import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.quiz.domain.Quiz;
import com.sendquiz.quiz.dto.request.QuizSave;
import com.sendquiz.quiz.repository.QuizRepository;
import com.sendquiz.quiz_filter.dto.QuizFilterSearch;
import com.sendquiz.quiz_filter.repository.QuizFilterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final EmailQuizSenderProd emailQuizSenderProd;
    private final CacheManager cacheManager;

    @Scheduled(fixedRate = ONE_DAY)
    @CacheEvict(value = QUIZ_CACHE, allEntries = true)
    public void flushCacheToDB() {
        log.info("flushCacheToDB");
    }

    @Cacheable(value = QUIZ_CACHE, key = QUIZ_LIST_KEY)
    public List<Quiz> findAll() {
        List<Quiz> quizList;
        if (requireNonNull(cacheManager.getCache(QUIZ_CACHE)).get(QUIZ_LIST) == null) {
            quizList = quizRepository.findAll();
            requireNonNull(cacheManager.getCache(QUIZ_CACHE)).put(QUIZ_LIST, quizList);
        } else {
            quizList = requireNonNull(cacheManager.getCache(QUIZ_CACHE)).get(QUIZ_LIST, List.class);
        }

        return quizList;
    }

    public void sendRandomQuizList() {
        List<QuizFilterSearch> quizFilterSearchList = quizFilterRepository.findAllQuizFilterSearch();
        for (QuizFilterSearch quizFilterSearch : quizFilterSearchList) {
            List<Quiz> filteredQuizList = getFilteredQuizList(quizFilterSearch);

            Collections.shuffle(filteredQuizList);
            List<Quiz> randomQuizList = filteredQuizList.stream()
                    .limit(quizFilterSearch.getNumOfProblem())
                    .toList();

            emailQuizSenderProd.sendQuizList(randomQuizList, quizFilterSearch.getEmail());
        }
    }

    private List<Quiz> getFilteredQuizList(QuizFilterSearch quizFilterSearch) {
        return new ArrayList<>(findAll()
                .stream()
                .filter(q -> {
                    if (quizFilterSearch.isNetwork() && q.getSubject() == Subject.NETWORK) {
                        return true;
                    }
                    if (quizFilterSearch.isDatabase() && q.getSubject() == Subject.DATA_BASE) {
                        return true;
                    }
                    if (quizFilterSearch.isOS() && q.getSubject() == Subject.OPERATING_SYSTEM) {
                        return true;
                    }
                    if (quizFilterSearch.isDataStructure() && q.getSubject() == Subject.DATA_STRUCTURE) {
                        return true;
                    }
                    if (quizFilterSearch.isJava() && q.getSubject() == Subject.JAVA) {
                        return true;
                    }
                    if (quizFilterSearch.isSpring() && q.getSubject() == Subject.SPRING) {
                        return true;
                    }
                    return false;
                })
                .toList());
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
