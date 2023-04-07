package com.sendquiz.global.jwt;

import lombok.Getter;

@Getter
public class JwtResponse {

    private final String accessToken;

    public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
