package com.sendquiz.email.application.test;

import com.sendquiz.certification.domain.Certification;
import com.sendquiz.certification.repository.CertificationRepository;
import com.sendquiz.email.application.EmailCertificationSender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.sendquiz.certification.domain.Certification.toCertification;
import static com.sendquiz.global.constant.CommonConstant.*;

@RequiredArgsConstructor
@Getter
@Service
public class EmailCertificationSenderTest implements EmailCertificationSender {

    private final CertificationRepository certificationRepository;

    private ThreadLocal<SimpleMailMessage> testMailSender = new ThreadLocal<>();

    @Transactional
    public void sendCertificationNum(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        String certificationNum = makeCertificationMessage(toEmail, message);
        saveCertificationNum(toEmail, certificationNum);
        testMailSender.set(message);
    }

    public String makeCertificationMessage(String toEmail, SimpleMailMessage message) {
        String certificationNum = makeUUID();
        message.setTo(toEmail);
        message.setSubject(EMAIL_SUBJECT_TEST);
        message.setText(CERTIFICATION_MESSAGE_TEST);
        return certificationNum;
    }

    public String makeUUID() {
        return UUID.randomUUID().toString().substring(0,8);
    }

    public void saveCertificationNum(String toEmail, String certificationNum) {
        if (certificationRepository.findByEmail(toEmail).isEmpty()) {
            Certification certification = toCertification(toEmail, certificationNum);
            certificationRepository.save(certification);
        } else {
            Certification psCertification = certificationRepository.getByEmail(toEmail);
            psCertification.updateNum(certificationNum);
        }
    }
}
