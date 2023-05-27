package com.sendquiz.jwt.application.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtResponse {

    private final String accessToken;

    @Builder
    private JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public static JwtResponse toJwtResponse(String accessToken) {
        return JwtResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
