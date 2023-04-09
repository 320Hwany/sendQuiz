package com.sendquiz.quiz.repository;

import com.sendquiz.quiz.domain.Quiz;
import com.sendquiz.quiz_filter.dto.QuizFilterSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class QuizRepositoryImpl implements QuizRepository {

    private final QuizJpaRepository quizJpaRepository;

    @Override
    public List<Quiz> getQuizList(QuizFilterSearch quizFilterSearch) {
        return null;
    }
}
