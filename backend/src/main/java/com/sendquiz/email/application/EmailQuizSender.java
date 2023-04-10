package com.sendquiz.email.application;

import com.sendquiz.quiz.domain.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sendquiz.global.constant.CommonConstant.EMAIL_SUBJECT;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class EmailQuizSender {

    private final JavaMailSender mailSender;

    public void sendQuizList(List<Quiz> randomQuizList, String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(EMAIL_SUBJECT);
        StringBuffer sb = makeText(randomQuizList);
        message.setText(String.valueOf(sb));
        mailSender.send(message);
    }

    protected StringBuffer makeText(List<Quiz> randomQuizList) {
        StringBuffer sb = new StringBuffer();
        for (Quiz quiz : randomQuizList) {
            sb.append("분야 :").append(quiz.getSubject()).append("\n");
            sb.append("문제 :").append(quiz.getProblem()).append("\n");
            sb.append("해답 :").append(quiz.getAnswer()).append("\n").append("\n");
        }
        return sb;
    }
}
