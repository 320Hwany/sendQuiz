package com.sendquiz.jwt.exception;

import com.sendquiz.global.exception.AuthenticationException;

import static com.sendquiz.global.constant.ErrorMessageConstant.REFRESH_TOKEN_AUTHENTICATION;

public class RefreshTokenAuthenticationException extends AuthenticationException {

    private static final String MESSAGE = REFRESH_TOKEN_AUTHENTICATION;

    public RefreshTokenAuthenticationException() {
        super(MESSAGE);
    }
}
