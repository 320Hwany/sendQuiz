package com.sendquiz.certification.application;

import com.sendquiz.certification.domain.Certification;
import com.sendquiz.certification.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sendquiz.certification.domain.Certification.toCertification;

@RequiredArgsConstructor
@Transactional
@Service
public class CertificationCommand {

    private final CertificationRepository certificationRepository;

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
