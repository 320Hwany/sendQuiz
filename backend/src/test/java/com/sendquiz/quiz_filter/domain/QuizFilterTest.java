package com.sendquiz.quiz_filter.domain;

import com.sendquiz.quiz_filter.dto.request.QuizFilterUpdate;
import org.assertj.core.api.Assertions;
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
                .network(false)
                .database(false)
                .operatingSystem(false)
                .dataStructure(false)
                .java(false)
                .spring(false)
                .numOfProblem(5)
                .build();

        // when
        quizFilter.update(quizFilterUpdate);

        // then
        assertThat(quizFilter.isNetwork()).isFalse();
        assertThat(quizFilter.isDatabase()).isFalse();
        assertThat(quizFilter.isOS()).isFalse();
        assertThat(quizFilter.isDataStructure()).isFalse();
        assertThat(quizFilter.isJava()).isFalse();
        assertThat(quizFilter.isSpring()).isFalse();
        assertThat(quizFilter.getNumOfProblem()).isEqualTo(5);
    }
}