package com.sendquiz.jwt.exception;

import com.sendquiz.global.exception.AuthenticationException;

import static com.sendquiz.global.eumtype.ErrorMessageConstant.ACCESS_TOKEN_AUTHENTICATION;

public class AccessTokenAuthenticationException extends AuthenticationException {

    public static final String MESSAGE = ACCESS_TOKEN_AUTHENTICATION.message;

    public AccessTokenAuthenticationException() {
        super(MESSAGE);
    }
}
