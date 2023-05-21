package com.sendquiz.jwt.domain;

import com.sendquiz.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class JwtRefreshToken extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jwt_refresh_token_id")
    private Long id;

    private String refreshToken;

    private Long memberId;

    @Builder
    private JwtRefreshToken(String refreshToken, Long memberId) {
        this.refreshToken = refreshToken;
        this.memberId = memberId;
    }

    public static JwtRefreshToken toEntity(String refreshToken, Long memberId) {
        return JwtRefreshToken.builder()
                .refreshToken(refreshToken)
                .memberId(memberId)
                .build();
    }

    public void update(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
