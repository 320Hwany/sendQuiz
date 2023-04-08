package com.sendquiz.quiz_filter.repository;

import com.sendquiz.quiz_filter.domain.QuizFilter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizFilterJpaRepository extends JpaRepository<QuizFilter, Long> {
}
