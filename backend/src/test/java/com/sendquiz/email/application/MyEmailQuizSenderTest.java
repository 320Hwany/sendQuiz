package com.sendquiz.email.application;

import com.sendquiz.email.application.test.EmailQuizSenderTest;
import com.sendquiz.quiz.domain.Quiz;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;
import java.util.stream.IntStream;

import static com.sendquiz.global.constant.CommonConstant.CERTIFICATION_MESSAGE_TEST;
import static com.sendquiz.global.constant.CommonConstant.EMAIL_SUBJECT_TEST;
import static org.assertj.core.api.Assertions.assertThat;

public class MyEmailQuizSenderTest {

    private EmailQuizSenderTest emailQuizSenderTest = new EmailQuizSenderTest();

    @Test
    @DisplayName("랜덤 퀴즈 리스트가 testEmailSender에 저장되는지를 확입합니다")
    void sendQuizList() {
        // given
        String toMail = "test email";
        List<Quiz> randomQuizList = IntStream.range(1, 3)
                .mapToObj(i -> Quiz.builder()
                        .problem("퀴즈 문제 " + i)
                        .build()).toList();

        // when
        emailQuizSenderTest.sendQuizList(randomQuizList, toMail);

        // then
        ThreadLocal<SimpleMailMessage> testMailSender = emailQuizSenderTest.getTestMailSender();
        SimpleMailMessage message = testMailSender.get();
        assertThat(message.getSubject()).isEqualTo(EMAIL_SUBJECT_TEST);
    }

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
