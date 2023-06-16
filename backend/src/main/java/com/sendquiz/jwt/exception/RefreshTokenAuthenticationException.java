package com.sendquiz.jwt.exception;

import com.sendquiz.global.exception.AuthenticationException;

import static com.sendquiz.global.eumtype.ErrorMessageConstant.REFRESH_TOKEN_AUTHENTICATION;

public class RefreshTokenAuthenticationException extends AuthenticationException {

    private static final String MESSAGE = REFRESH_TOKEN_AUTHENTICATION.message;

    public RefreshTokenAuthenticationException() {
        super(MESSAGE);
    }
}
