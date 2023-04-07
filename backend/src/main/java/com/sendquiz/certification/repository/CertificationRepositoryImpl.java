package com.sendquiz.certification.repository;

import com.sendquiz.certification.domain.Certification;
import com.sendquiz.certification.exception.CertificationNotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CertificationRepositoryImpl implements CertificationRepository {

    private final CertificationJpaRepository certificationJpaRepository;

    @Override
    public void save(Certification certification) {
        certificationJpaRepository.save(certification);
    }

    @Override
    public Certification getByEmail(String email) {
        return certificationJpaRepository.findByEmail(email)
                .orElseThrow(CertificationNotMatchException::new);
    }

    @Override
    public Optional<Certification> findByEmail(String email) {
        return certificationJpaRepository.findByEmail(email);
    }
}
