package com.sendquiz.jwt.repository;

import com.sendquiz.jwt.domain.JwtRefreshToken;

import java.util.Optional;

public interface JwtRepository {

    void save(JwtRefreshToken jwtRefreshToken);

    Optional<JwtRefreshToken> findByMemberId(Long memberId);

    JwtRefreshToken getByMemberId(Long memberId);

    JwtRefreshToken getById(Long id);

    void delete(JwtRefreshToken jwtRefreshToken);
}
