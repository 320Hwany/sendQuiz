package com.sendquiz.member.exception;

import com.sendquiz.global.exception.DuplicationException;

import static com.sendquiz.global.eumtype.ErrorMessageConstant.MEMBER_DUPLICATION;


public class MemberDuplicationException extends DuplicationException {

    private static final String MESSAGE = MEMBER_DUPLICATION.message;

    public MemberDuplicationException() {
        super(MESSAGE);
    }
}
