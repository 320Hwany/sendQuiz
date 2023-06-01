package com.sendquiz.email.application.prod;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.simpleemail.model.*;
import com.sendquiz.certification.domain.Certification;
import com.sendquiz.certification.repository.CertificationRepository;
import com.sendquiz.email.application.EmailCertificationSender;
import com.sendquiz.email.exception.EmailMessageException;
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

import java.util.UUID;

import static com.sendquiz.certification.domain.Certification.toCertification;
import static com.sendquiz.global.constant.CommonConstant.*;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Primary
@Service
public class EmailCertificationSenderProd implements EmailCertificationSender {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final CertificationRepository certificationRepository;

    @Transactional
    public void sendCertificationNum(String toEmail) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8);
            String certificationNum = makeUUID();
            saveCertificationNum(toEmail, certificationNum);
            helper.setTo(toEmail);
            helper.setSubject(EMAIL_SUBJECT);
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

    @Transactional
    public void saveCertificationNum(String toEmail, String certificationNum) {
        if (certificationRepository.findByEmail(toEmail).isEmpty()) {
            Certification certification = toCertification(toEmail, certificationNum);
            certificationRepository.save(certification);
        } else {
            Certification psCertification = certificationRepository.getByEmail(toEmail);
            psCertification.updateNum(certificationNum);
        }
    }

    public String setContext(String certificationNum) {
        Context context = new Context();
        context.setVariable(CERTIFICATION_NUM, certificationNum);
        return templateEngine.process(CERTIFICATION, context);
    }
}

