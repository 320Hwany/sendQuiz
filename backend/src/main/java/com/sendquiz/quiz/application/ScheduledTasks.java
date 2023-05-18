package com.sendquiz.quiz.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.sendquiz.global.constant.CommonConstant.SEVEN_AM;

@EnableScheduling
@RequiredArgsConstructor
@Component
public class ScheduledTasks {

    private final QuizService quizService;

    @Scheduled(cron = SEVEN_AM)
    public void sendQuizAt7AM() {
        quizService.sendRandomQuizList();
    }
}