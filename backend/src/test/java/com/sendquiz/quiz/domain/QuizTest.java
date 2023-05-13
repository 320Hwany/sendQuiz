package com.sendquiz.quiz.domain;

import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.quiz_filter.application.request.QuizFilterSearch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class QuizTest {

    @Test
    @DisplayName("해당 퀴즈가 퀴즈 필터 조건에 맞으면 true를 반환합니다")
    void filterQuizList() {
        // given
        Quiz quiz = Quiz.builder()
                .subject(Subject.NETWORK)
                .build();

        QuizFilterSearch quizFilterSearch1 = QuizFilterSearch.builder()
                .isNetwork(true)
                .build();

        QuizFilterSearch quizFilterSearch2 = QuizFilterSearch.builder()
                .isNetwork(false)
                .build();

        // when
        boolean conditionMeet = quiz.filterQuizList(quizFilterSearch1);
        boolean conditionNotMeet = quiz.filterQuizList(quizFilterSearch2);

        // then
        assertThat(conditionMeet).isTrue();
        assertThat(conditionNotMeet).isFalse();
    }
}