package com.sendquiz.jwt.exception;

import com.sendquiz.global.exception.AuthenticationException;

import static com.sendquiz.global.constant.ErrorMessageConstant.COOKIE_EXPIRED_MESSAGE;

public class CookieExpiredException extends AuthenticationException {

    private static final String MESSAGE = COOKIE_EXPIRED_MESSAGE;

    public CookieExpiredException() {
        super(MESSAGE);
    }
}
