package com.sendquiz.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtResponse {

    private final String accessToken;

    @Builder
    public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public static JwtResponse toJwtResponse(String accessToken) {
        return JwtResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
