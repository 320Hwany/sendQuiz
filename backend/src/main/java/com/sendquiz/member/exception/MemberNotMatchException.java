package com.sendquiz.member.exception;

import com.sendquiz.global.exception.NotMatchException;

import static com.sendquiz.global.eumtype.ErrorMessageConstant.*;

public class MemberNotMatchException extends NotMatchException {

    public static final String MESSAGE = MEMBER_NOT_MATCH.message;

    public MemberNotMatchException() {
        super(MESSAGE);
    }
}
