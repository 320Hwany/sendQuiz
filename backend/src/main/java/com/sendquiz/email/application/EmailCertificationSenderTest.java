package com.sendquiz.email.application;

import com.sendquiz.certification.domain.Certification;
import com.sendquiz.certification.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.sendquiz.certification.domain.Certification.toCertification;
import static com.sendquiz.global.constant.CommonConstant.*;

@RequiredArgsConstructor
@Service
public class EmailCertificationSenderTest implements EmailCertificationSender {

    private final CertificationRepository certificationRepository;

    @Override
    @Transactional
    public void sendCertificationNum(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        String certificationNum = makeCertificationMessage(toEmail, message);
        saveCertificationNum(toEmail, certificationNum);
    }

    @Override
    public String makeCertificationMessage(String toEmail, SimpleMailMessage message) {
        String certificationNum = makeUUID();
        message.setTo(toEmail);
        message.setSubject(EMAIL_SUBJECT_TEST);
        message.setText(CERTIFICATION_MESSAGE_TEST);
        return certificationNum;
    }

    @Override
    public String makeUUID() {
        return UUID.randomUUID().toString().substring(0,8);
    }

    @Override
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
