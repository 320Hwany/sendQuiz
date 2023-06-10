package com.sendquiz.email.application;

import com.sendquiz.quiz.domain.Quiz;

import java.util.List;

public interface EmailQuizSender {

    void sendQuizzes(List<Quiz> randomQuizList, String toEmail);

    String setContext(List<Quiz> randomQuizList);
}
