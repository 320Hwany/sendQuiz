package com.sendquiz.member.exception;

import com.sendquiz.global.exception.NotMatchException;

import static com.sendquiz.global.constant.ErrorMessageConstant.PASSWORD_NOT_MATCH_MESSAGE;

public class PasswordNotMatchException extends NotMatchException {

    public static final String MESSAGE = PASSWORD_NOT_MATCH_MESSAGE;

    public PasswordNotMatchException() {
        super(MESSAGE);
    }
}
