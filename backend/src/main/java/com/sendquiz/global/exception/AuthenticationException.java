package com.sendquiz.global.exception;

import lombok.Getter;

import static com.sendquiz.global.eumtype.StatusCodeConstant.AUTHENTICATION;

@Getter
public abstract class AuthenticationException extends RuntimeException {

    private final String statusCode = AUTHENTICATION.statusCode;
    private String message;

    public AuthenticationException(String message) {
        this.message = message;
    }
}
