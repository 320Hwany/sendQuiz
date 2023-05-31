package com.sendquiz.email.application.prod;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.sendquiz.email.application.EmailQuizSender;
import com.sendquiz.email.exception.EmailMessageException;
import com.sendquiz.quiz.domain.Quiz;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
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
@Primary
@Service
public class EmailQuizSenderProd implements EmailQuizSender {

    private final AmazonSimpleEmailService amazonSimpleEmailService;
    private final SpringTemplateEngine templateEngine;

    public void sendQuizList(List<Quiz> randomQuizList, String toEmail) {
        SendEmailRequest emailRequest = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(toEmail))
                .withMessage(new Message()
                        .withSubject(new Content().withData(EMAIL_SUBJECT))
                        .withBody(new Body().withHtml(new Content().withData(setContext(randomQuizList))))
                )
                .withSource(FROM_EMAIL_ADDRESS);

        try {
            amazonSimpleEmailService.sendEmail(emailRequest);
        } catch (AmazonClientException e) {
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


