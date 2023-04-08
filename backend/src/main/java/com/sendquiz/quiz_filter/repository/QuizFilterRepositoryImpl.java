package com.sendquiz.quiz_filter.repository;

import com.sendquiz.quiz_filter.domain.QuizFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class QuizFilterRepositoryImpl implements QuizFilterRepository {

    private final QuizFilterJpaRepository quizFilterJpaRepository;

    @Override
    public void save(QuizFilter quizFilter) {
        quizFilterJpaRepository.save(quizFilter);
    }
}
