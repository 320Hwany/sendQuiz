package com.sendquiz.member.exception;

import com.sendquiz.global.exception.AuthenticationException;

import static com.sendquiz.global.constant.ErrorMessageConstant.MEMBER_AUTHENTICATION_MESSAGE;

public class MemberAuthenticationException extends AuthenticationException {

    private static final String MESSAGE = MEMBER_AUTHENTICATION_MESSAGE;

    public MemberAuthenticationException() {
        super(MESSAGE);
    }
}
