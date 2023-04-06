package com.sendquiz.certification.repository;

import com.sendquiz.certification.domain.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationJpaRepository extends JpaRepository<Certification, Long> {
}
