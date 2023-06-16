package com.sendquiz.jwt.exception;

import com.sendquiz.global.exception.AuthenticationException;

import static com.sendquiz.global.eumtype.ErrorMessageConstant.*;

public class CookieExpiredException extends AuthenticationException {

    private static final String MESSAGE = COOKIE_EXPIRED.message;

    public CookieExpiredException() {
        super(MESSAGE);
    }
}
