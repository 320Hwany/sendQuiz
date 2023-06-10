package com.sendquiz.email.application.prod;

import com.sendquiz.email.application.EmailQuizSender;
import com.sendquiz.email.exception.EmailMessageException;
import com.sendquiz.quiz.domain.Quiz;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

import static com.sendquiz.global.constant.CommonConstant.*;
import static com.sendquiz.global.constant.CommonConstant.EMAIL_SUBJECT;
import static com.sendquiz.global.constant.CommonConstant.QUIZ_LIST;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Primary
@Service
public class EmailQuizSenderProd implements EmailQuizSender {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendQuizzes(List<Quiz> randomQuizList, String toEmail) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8);
            helper.setTo(toEmail);
            helper.setSubject(EMAIL_SUBJECT);
            helper.setText(setContext(randomQuizList), true);
            mailSender.send(message);
            log.info("sendQuizList success");
        } catch (MessagingException e) {
            log.error("Error Message={}", e.getMessage());
            throw new EmailMessageException();
        }
    }

    @Override
    public String setContext(List<Quiz> randomQuizList) {
        Context context = new Context();
        context.setVariable(QUIZ_LIST, randomQuizList);
        return templateEngine.process(QUIZ_LIST, context);
    }
}


