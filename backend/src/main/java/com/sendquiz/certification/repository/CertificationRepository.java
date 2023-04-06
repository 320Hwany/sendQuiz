package com.sendquiz.certification.repository;

import com.sendquiz.certification.domain.Certification;

import java.util.Optional;

public interface CertificationRepository {

    void save(Certification certification);

    Certification getByEmail(String email);
}
