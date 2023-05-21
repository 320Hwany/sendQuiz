package com.sendquiz.jwt.application.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtResponse {

    private final String accessToken;

    private final String refreshTokenId;

    @Builder
    private JwtResponse(String accessToken, String refreshTokenId) {
        this.accessToken = accessToken;
        this.refreshTokenId = refreshTokenId;
    }

    public static JwtResponse toJwtResponse(String accessToken, String refreshTokenId) {
        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshTokenId(refreshTokenId)
                .build();
    }
}
