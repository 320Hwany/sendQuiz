package com.sendquiz.member.exception;

import com.sendquiz.global.exception.DuplicationException;

import static com.sendquiz.global.util.ErrorMessageConstant.MEMBER_DUPLICATION_MESSAGE;

public class MemberDuplicationException extends DuplicationException {

    private static final String message = MEMBER_DUPLICATION_MESSAGE;

    public MemberDuplicationException() {
        super(message);
    }
}
