package com.sendquiz.member.exception;

import com.sendquiz.global.exception.AuthenticationException;

import static com.sendquiz.global.eumtype.ErrorMessageConstant.*;


public class MemberAuthenticationException extends AuthenticationException {

    private static final String MESSAGE = MEMBER_AUTHENTICATION.message;

    public MemberAuthenticationException() {
        super(MESSAGE);
    }
}
