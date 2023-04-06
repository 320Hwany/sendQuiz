package com.sendquiz.certification.repository;

import com.sendquiz.certification.domain.Certification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CertificationRepositoryImpl implements CertificationRepository {

    private final CertificationJpaRepository certificationJpaRepository;

    @Override
    public void save(Certification certification) {
        certificationJpaRepository.save(certification);
    }
}
