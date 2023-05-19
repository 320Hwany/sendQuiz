package com.sendquiz.jwt.exception;

import com.sendquiz.global.exception.AuthenticationException;

import static com.sendquiz.global.constant.ErrorMessageConstant.ACCESS_TOKEN_AUTHENTICATION;

public class AccessTokenAuthenticationException extends AuthenticationException {

    public static final String MESSAGE = ACCESS_TOKEN_AUTHENTICATION;

    public AccessTokenAuthenticationException() {
        super(MESSAGE);
    }
}
