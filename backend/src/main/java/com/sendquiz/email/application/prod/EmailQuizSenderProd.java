package com.sendquiz.email.application.prod;

import com.sendquiz.email.application.EmailQuizSender;
import com.sendquiz.quiz.domain.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sendquiz.global.constant.CommonConstant.EMAIL_SUBJECT;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Primary
@Service
public class EmailQuizSenderProd implements EmailQuizSender {

    private final JavaMailSender mailSender;

    public void sendQuizList(List<Quiz> randomQuizList, String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(EMAIL_SUBJECT);
        StringBuffer sb = makeText(randomQuizList);
        message.setText(String.valueOf(sb));
        mailSender.send(message);
    }

    public StringBuffer makeText(List<Quiz> randomQuizList) {
        StringBuffer sb = new StringBuffer();
        for (Quiz quiz : randomQuizList) {
            sb.append("-------------------------------------------").append("\n");
            sb.append("분야 : ").append(quiz.getSubject().getValue()).append("\n");
            sb.append("문제 : ").append(quiz.getProblem()).append("\n");
            sb.append("답안").append("\n").append(quiz.getAnswer()).append("\n").append("\n");
        }
        return sb;
    }
}
