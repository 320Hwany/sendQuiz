package com.sendquiz.email.application.prod;

import com.sendquiz.certification.application.CertificationService;
import com.sendquiz.email.application.EmailCertificationSender;
import com.sendquiz.email.exception.EmailMessageException;
import com.sendquiz.global.eumtype.constant.InfraConstant;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.UUID;

import static com.sendquiz.global.eumtype.constant.EmailConstant.*;
import static com.sendquiz.global.eumtype.constant.InfraConstant.*;

@Slf4j
@RequiredArgsConstructor
@Primary
@Service
public class EmailCertificationSenderProd implements EmailCertificationSender {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final CertificationService certificationService;

    public void sendCertificationNum(String toEmail) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8.message);
            String certificationNum = makeUUID();
            certificationService.putCertificationNumberToCache(toEmail, certificationNum);
            helper.setTo(toEmail);
            helper.setSubject(EMAIL_SUBJECT.message);
            helper.setText(setContext(certificationNum), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error Message={}", e.getMessage());
            throw new EmailMessageException();
        }
    }

    public String makeUUID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public String setContext(String certificationNum) {
        Context context = new Context();
        context.setVariable(CERTIFICATION_NUM.message, certificationNum);
        return templateEngine.process(CERTIFICATION.message, context);
    }
}

