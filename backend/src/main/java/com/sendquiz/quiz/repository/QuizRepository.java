package com.sendquiz.quiz.repository;

import com.sendquiz.quiz.domain.Quiz;
import com.sendquiz.quiz_filter.dto.QuizFilterSearch;

import java.util.List;

public interface QuizRepository {

    List<Quiz> findRandomQuizList(QuizFilterSearch quizFilterSearch);

    void save(Quiz quiz);

    void saveAll(List<Quiz> quizList);
}
