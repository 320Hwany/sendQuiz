package com.sendquiz.member.exception;

import com.sendquiz.global.exception.DuplicationException;

import static com.sendquiz.global.constant.ErrorMessageConstant.MEMBER_DUPLICATION_MESSAGE;

public class MemberDuplicationException extends DuplicationException {

    private static final String MESSAGE = MEMBER_DUPLICATION_MESSAGE;

    public MemberDuplicationException() {
        super(MESSAGE);
    }
}
