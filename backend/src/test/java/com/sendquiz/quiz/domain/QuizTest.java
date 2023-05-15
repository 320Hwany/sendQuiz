package com.sendquiz.quiz.domain;

import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.quiz.presentation.request.QuizUpdate;
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

    @Test
    @DisplayName("퀴즈 정보를 업데이트합니다")
    void update() {
        // given
        Quiz quiz = Quiz.builder()
                .problem("수정전 문제")
                .answer("수정전 답")
                .subject(Subject.JAVA)
                .build();

        QuizUpdate quizUpdate = QuizUpdate.builder()
                .problem("수정후 문제")
                .answer("수정후 답")
                .build();

        // when
        quiz.update(quizUpdate, Subject.NETWORK);

        // then
        assertThat(quiz).extracting("problem", "answer", "subject")
                .contains("수정후 문제", "수정후 답", Subject.NETWORK);
    }
}