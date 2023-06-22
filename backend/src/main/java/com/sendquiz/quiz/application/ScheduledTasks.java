package com.sendquiz.quiz.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.sendquiz.global.eumtype.constant.CacheConstant.QUIZ_CACHE;
import static com.sendquiz.global.eumtype.constant.TimeConstant.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScheduledTasks {

    private final QuizQuery quizQuery;

    @Async
    @Scheduled(cron = NINE_AM)
    public void sendQuizAt9AM() {
        log.info("NINE_AM sendQuizAt9AM");
        quizQuery.sendRandomQuizzes();
    }

    @Scheduled(cron = SEVEN_AM)
    @CacheEvict(value = QUIZ_CACHE, allEntries = true)
    public void clearQuizCache() {
        log.info("SEVEN_AM clearQuizCache");
    }

    @Scheduled(cron = EIGHT_AM)
    public void putCache() {
        log.info("EIGHT_AM putCache");
        quizQuery.findAll();
    }
}