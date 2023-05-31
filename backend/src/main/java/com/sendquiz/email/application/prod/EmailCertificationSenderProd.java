package com.sendquiz.email.application.prod;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.sendquiz.certification.domain.Certification;
import com.sendquiz.certification.repository.CertificationRepository;
import com.sendquiz.email.application.EmailCertificationSender;
import com.sendquiz.email.exception.EmailMessageException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.UUID;

import static com.sendquiz.certification.domain.Certification.toCertification;
import static com.sendquiz.global.constant.CommonConstant.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Primary
@Service
public class EmailCertificationSenderProd implements EmailCertificationSender {

    private final AmazonSimpleEmailService amazonSimpleEmailService;
    private final SpringTemplateEngine templateEngine;
    private final CertificationRepository certificationRepository;

    @Transactional
    public void sendCertificationNum(String toEmail) {
        String certificationNum = makeUUID();
        saveCertificationNum(toEmail, certificationNum);

        SendEmailRequest emailRequest = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(toEmail))
                .withMessage(new Message()
                        .withSubject(new Content().withData(EMAIL_SUBJECT))
                        .withBody(new Body().withHtml(new Content().withData(setContext(certificationNum))))
                )
                .withSource(FROM_EMAIL_ADDRESS);

        try {
            amazonSimpleEmailService.sendEmail(emailRequest);
        } catch (AmazonClientException e) {
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

