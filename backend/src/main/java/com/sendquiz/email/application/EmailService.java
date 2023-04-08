package com.sendquiz.email.application;

import com.sendquiz.certification.domain.Certification;
import com.sendquiz.certification.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.sendquiz.certification.domain.Certification.toCertification;
import static com.sendquiz.global.constant.CommonConstant.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final CertificationRepository certificationRepository;

    @Transactional
    public void sendEmail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        String certificationNum = makeMessage(toEmail, message);
        saveCertificationNum(toEmail, certificationNum);
        mailSender.send(message);
    }

    protected String makeMessage(String toEmail, SimpleMailMessage message) {
        String certificationNum = makeUUID();
        message.setTo(toEmail);
        message.setSubject(EMAIL_SUBJECT);
        message.setText(CERTIFICATION_MESSAGE + certificationNum);
        return certificationNum;
    }

    protected String makeUUID() {
        return UUID.randomUUID().toString().substring(0,8);
    }

    protected void saveCertificationNum(String toEmail, String certificationNum) {
        if (certificationRepository.findByEmail(toEmail).isEmpty()) {
            Certification certification = toCertification(toEmail, certificationNum);
            certificationRepository.save(certification);
        } else {
            Certification psCertification = certificationRepository.getByEmail(toEmail);
            psCertification.updateNum(certificationNum);
        }
    }
}