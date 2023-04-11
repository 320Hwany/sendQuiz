package com.sendquiz.email.application;

import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.quiz.domain.Quiz;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class MyEmailQuizSenderTest {

    @InjectMocks
    private EmailQuizSenderTest emailQuizSenderTest;

    @Test
    @DisplayName("랜덤 퀴즈 리스트로 이메일의 텍스트를 생성합니다")
    void makeText() {
        // given
        List<Quiz> randomQuizList = IntStream.range(1, 3)
                .mapToObj(i -> Quiz.builder()
                        .problem("퀴즈 문제 " + i)
                        .build()).toList();

        // when
        StringBuffer sb = emailQuizSenderTest.makeText(randomQuizList);

        // then
        assertThat(sb).isNotBlank();
    }
}
