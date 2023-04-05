package com.sendquiz.member.exception;

import com.sendquiz.global.exception.NotMatchException;

import static com.sendquiz.global.constant.ErrorMessageConstant.MEMBER_NOT_MATCH_MESSAGE;

public class MemberNotMatchException extends NotMatchException {

    public static final String MESSAGE = MEMBER_NOT_MATCH_MESSAGE;

    public MemberNotMatchException() {
        super(MESSAGE);
    }
}
