package com.sendquiz.quiz_filter.domain;

import com.sendquiz.quiz_filter.dto.request.QuizFilterUpdate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;


class QuizFilterTest {

    @Test
    @DisplayName("QuizFilter를 업데이트합니다")
    void update() {
        // given
        QuizFilter quizFilter = QuizFilter.builder()
                .isNetwork(TRUE)
                .isDatabase(TRUE)
                .isOS(TRUE)
                .isDataStructure(TRUE)
                .isJava(TRUE)
                .isSpring(TRUE)
                .numOfProblem(7)
                .build();

        QuizFilterUpdate quizFilterUpdate = QuizFilterUpdate.builder()
                .isNetwork(FALSE)
                .isDatabase(FALSE)
                .isOS(FALSE)
                .isDataStructure(FALSE)
                .isJava(FALSE)
                .isSpring(FALSE)
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