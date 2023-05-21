package com.sendquiz.jwt.repository;

import com.sendquiz.jwt.domain.JwtRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtJpaRepository extends JpaRepository<JwtRefreshToken, Long> {

    Optional<JwtRefreshToken> findByMemberId(Long memberId);
}
