package com.sendquiz.email.exception;

import com.sendquiz.global.exception.NotMatchException;

import static com.sendquiz.global.constant.ErrorMessageConstant.MEMBER_NOT_FOUND_MESSAGE;

public class EmailNotMatchException extends NotMatchException {

    private static final String MESSAGE = MEMBER_NOT_FOUND_MESSAGE;

    public EmailNotMatchException() {
        super(MESSAGE);
    }
}
