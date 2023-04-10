package com.sendquiz.member.exception;

import com.sendquiz.global.exception.AuthenticationException;

import static com.sendquiz.global.constant.ErrorMessageConstant.ADMIN_AUTHENTICATION_MESSAGE;

public class AdminAuthenticationException extends AuthenticationException {

    private static final String MESSAGE = ADMIN_AUTHENTICATION_MESSAGE;
    public AdminAuthenticationException() {
        super(MESSAGE);
    }
}
