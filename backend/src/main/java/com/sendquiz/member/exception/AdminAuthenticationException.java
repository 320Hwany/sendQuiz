package com.sendquiz.member.exception;

import com.sendquiz.global.exception.AuthenticationException;

import static com.sendquiz.global.eumtype.ErrorMessageConstant.ADMIN_AUTHENTICATION;

public class AdminAuthenticationException extends AuthenticationException {

    private static final String MESSAGE = ADMIN_AUTHENTICATION.message;
    public AdminAuthenticationException() {
        super(MESSAGE);
    }
}
