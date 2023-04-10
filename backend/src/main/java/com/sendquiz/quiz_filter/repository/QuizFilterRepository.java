package com.sendquiz.quiz_filter.repository;

import com.sendquiz.quiz_filter.domain.QuizFilter;
import com.sendquiz.quiz_filter.dto.QuizFilterSearch;

import java.util.List;

public interface QuizFilterRepository {

    void save(QuizFilter quizFilter);

    void saveAll(List<QuizFilter> quizFilterList);

    List<QuizFilterSearch> findAllQuizFilterSearch();
}
