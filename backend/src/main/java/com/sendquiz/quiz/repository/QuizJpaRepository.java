package com.sendquiz.quiz.repository;

import com.sendquiz.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizJpaRepository extends JpaRepository<Quiz, Long> {
}
