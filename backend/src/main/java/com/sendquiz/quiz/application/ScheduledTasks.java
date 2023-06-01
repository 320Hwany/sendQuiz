package com.sendquiz.quiz.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.sendquiz.global.constant.CommonConstant.NINE_AM;


@RequiredArgsConstructor
@Component
public class ScheduledTasks {

    private final QuizQuery quizQuery;

    @Async
    @Scheduled(cron = NINE_AM)
    public void sendQuizAt9AM() {
        quizQuery.sendRandomQuizList();
    }
}