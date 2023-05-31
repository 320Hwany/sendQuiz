package com.sendquiz.quiz.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.sendquiz.global.constant.CommonConstant.TWELVE_AM;

@RequiredArgsConstructor
@Component
public class ScheduledTasks {

    private final QuizService quizService;

    @Scheduled(cron = TWELVE_AM)
    public void sendQuizAt12AM() {
        quizService.sendRandomQuizList();
    }
}