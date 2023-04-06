package com.sendquiz.certification.repository;

import com.sendquiz.certification.domain.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificationJpaRepository extends JpaRepository<Certification, Long> {

    Optional<Certification> findByEmail(String email);
}
