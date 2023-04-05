package com.sendquiz.global.exception;

import lombok.Getter;

import static com.sendquiz.global.constant.StatusCodeConstant.AUTHENTICATION_STATUS_CODE;

@Getter
public abstract class AuthenticationException extends RuntimeException {

    private final String statusCode = AUTHENTICATION_STATUS_CODE;
    private String message;

    public AuthenticationException(String message) {
        this.message = message;
    }
}
