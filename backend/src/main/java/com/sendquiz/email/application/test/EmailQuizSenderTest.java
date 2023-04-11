package com.sendquiz.email.application.test;


import com.sendquiz.email.application.EmailQuizSender;
import com.sendquiz.quiz.domain.Quiz;
import lombok.Getter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sendquiz.global.constant.CommonConstant.EMAIL_SUBJECT_TEST;

@Getter
@Service
public class EmailQuizSenderTest implements EmailQuizSender {

    private ThreadLocal<SimpleMailMessage> testMailSender = new ThreadLocal<>();

    public void sendQuizList(List<Quiz> randomQuizList, String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(EMAIL_SUBJECT_TEST);
        StringBuffer sb = makeText(randomQuizList);
        message.setText(String.valueOf(sb));
        testMailSender.set(message);
    }

    public StringBuffer makeText(List<Quiz> randomQuizList) {
        StringBuffer sb = new StringBuffer();
        for (Quiz quiz : randomQuizList) {
            sb.append(quiz.getProblem());
        }
        return sb;
    }
}
