package com.sendquiz.quiz.application;

import com.sendquiz.quiz.domain.Quiz;
import com.sendquiz.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.sendquiz.global.constant.CommonConstant.*;
import static java.util.Objects.requireNonNull;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScheduledTasks {

    private final QuizQuery quizQuery;
    private final QuizRepository quizRepository;
    private final CacheManager cacheManager;

    @Async
    @Scheduled(cron = NINE_AM)
    public void sendQuizAt9AM() {
        log.info("NINE_AM sendQuizAt9AM");
        quizQuery.sendRandomQuizzes();
    }

    @Scheduled(cron = EIGHT_AM)
    public void updateCache() {
        clearQuizCache();
        putCache();
    }

    @CacheEvict(value = QUIZ_CACHE, allEntries = true)
    public void clearQuizCache() {
        log.info("EIGHT_AM clearQuizCache");
    }

    public void putCache() {
        Cache cache = requireNonNull(cacheManager.getCache(QUIZ_CACHE));
        List<Quiz> quizList = cache.get(QUIZ_LIST, List.class);

        if (quizList == null) {
            quizList = quizRepository.findAll();
            cache.put(QUIZ_LIST, quizList);
            log.info("EIGHT_AM putCache");
        }
    }
}