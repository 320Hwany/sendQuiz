package com.sendquiz.quiz.repository;

import com.sendquiz.quiz.domain.Quiz;
import com.sendquiz.quiz_filter.application.request.QuizFilterSearch;

import java.util.List;

public interface QuizRepository {

    List<Quiz> findRandomQuizList(QuizFilterSearch quizFilterSearch);

    List<Quiz> findAll();

    void save(Quiz quiz);

    void saveAll(List<Quiz> quizList);
}
