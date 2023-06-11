package com.sendquiz.quiz_filter.domain;

import com.sendquiz.quiz_filter.dto.request.QuizFilterUpdate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class QuizFilterTest {

    @Test
    @DisplayName("QuizFilter를 업데이트합니다")
    void update() {
        // given
        QuizFilter quizFilter = QuizFilter.builder()
                .isNetwork(true)
                .isDatabase(true)
                .isOS(true)
                .isDataStructure(true)
                .isJava(true)
                .isSpring(true)
                .numOfProblem(7)
                .build();

        QuizFilterUpdate quizFilterUpdate = QuizFilterUpdate.builder()
                .isNetwork(false)
                .isDatabase(false)
                .isOS(false)
                .isDataStructure(false)
                .isJava(false)
                .isSpring(false)
                .numOfProblem(5)
                .build();

        // when
        quizFilter.update(quizFilterUpdate);

        // then
        assertThat(quizFilter.getIsNetwork()).isFalse();
        assertThat(quizFilter.getIsDatabase()).isFalse();
        assertThat(quizFilter.getIsOS()).isFalse();
        assertThat(quizFilter.getIsDataStructure()).isFalse();
        assertThat(quizFilter.getIsJava()).isFalse();
        assertThat(quizFilter.getIsSpring()).isFalse();
        assertThat(quizFilter.getNumOfProblem()).isEqualTo(5);
    }
}