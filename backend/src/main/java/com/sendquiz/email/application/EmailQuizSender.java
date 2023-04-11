package com.sendquiz.email.application;

import com.sendquiz.quiz.domain.Quiz;

import java.util.List;

public interface EmailQuizSender {

    void sendQuizList(List<Quiz> randomQuizList, String toEmail);

    StringBuffer makeText(List<Quiz> randomQuizList);
}
