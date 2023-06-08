package com.sendquiz.quiz.application;

import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.quiz.domain.Quiz;
import com.sendquiz.quiz.exception.SubjectNotMatchException;
import com.sendquiz.quiz.dto.request.QuizUpdate;
import com.sendquiz.quiz.repository.QuizRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuizCommandTest {

    @InjectMocks
    private QuizCommand quizCommand;

    @Mock
    private QuizRepository quizRepository;

    @Test
    @DisplayName("퀴즈가 존재하고 입력한 분야에 맞다면 퀴즈를 수정합니다")
    void update() {
        // given
        Quiz quiz = Quiz.builder()
                .problem("수정전 문제")
                .answer("수정전 답")
                .subject(Subject.JAVA)
                .build();

        QuizUpdate quizUpdate = QuizUpdate.builder()
                .subject("네트워크")
                .problem("수정후 문제")
                .answer("수정후 답")
                .build();

        // stub
        when(quizRepository.getById(any())).thenReturn(quiz);

        // when
        quizCommand.update(quizUpdate);

        // then
        assertThat(quiz).extracting("problem", "answer", "subject")
                .contains("수정후 문제", "수정후 답", Subject.NETWORK);
    }

    @Test
    @DisplayName("입력한 퀴즈 분야가 맞지 않으면 퀴즈를 수정할 수 없습니다")
    void updateFailNotMatchSubject() {
        // given
        Quiz quiz = Quiz.builder()
                .problem("수정전 문제")
                .answer("수정전 답")
                .subject(Subject.JAVA)
                .build();

        QuizUpdate quizUpdate = QuizUpdate.builder()
                .subject("분야가 맞지 않음")
                .problem("수정후 문제")
                .answer("수정후 답")
                .build();

        // stub
        when(quizRepository.getById(any())).thenReturn(quiz);

        // then
        assertThatThrownBy(() -> quizCommand.update(quizUpdate))
                .isInstanceOf(SubjectNotMatchException.class);
    }
}
