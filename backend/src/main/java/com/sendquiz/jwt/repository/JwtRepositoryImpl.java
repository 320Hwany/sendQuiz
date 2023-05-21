package com.sendquiz.jwt.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sendquiz.jwt.domain.JwtRefreshToken;
import com.sendquiz.jwt.domain.QJwtRefreshToken;
import com.sendquiz.jwt.exception.RefreshTokenAuthenticationException;
import com.sendquiz.jwt.exception.RefreshTokenNotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@RequiredArgsConstructor
@Repository
public class JwtRepositoryImpl implements JwtRepository {

    private final JwtJpaRepository jwtJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public void save(JwtRefreshToken jwtRefreshToken) {
        jwtJpaRepository.save(jwtRefreshToken);
    }

    @Override
    public Optional<JwtRefreshToken> findByMemberId(Long memberId) {
        return jwtJpaRepository.findByMemberId(memberId);
    }

    @Override
    public JwtRefreshToken getByMemberId(Long memberId) {
        return jwtJpaRepository.findByMemberId(memberId)
                .orElseThrow(RefreshTokenNotMatchException::new);
    }

    @Override
    public JwtRefreshToken getById(Long id) {
        return jwtJpaRepository.findById(id)
                .orElseThrow(RefreshTokenAuthenticationException::new);
    }

    @Override
    public void delete(JwtRefreshToken jwtRefreshToken) {
        jwtJpaRepository.delete(jwtRefreshToken);
    }
}
