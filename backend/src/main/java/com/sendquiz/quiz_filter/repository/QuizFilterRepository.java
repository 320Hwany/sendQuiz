package com.sendquiz.quiz_filter.repository;

import com.sendquiz.quiz_filter.domain.QuizFilter;
import com.sendquiz.quiz_filter.dto.QuizFilterSearch;

import java.util.List;
import java.util.Optional;

public interface QuizFilterRepository {

    void save(QuizFilter quizFilter);

    void saveAll(List<QuizFilter> quizFilterList);

    Optional<QuizFilter> findByMemberId(Long memberId);

    List<QuizFilterSearch> findAllQuizFilterSearch();
}
