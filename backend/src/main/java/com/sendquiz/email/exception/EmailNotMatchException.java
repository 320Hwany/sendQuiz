package com.sendquiz.email.exception;

import com.sendquiz.global.exception.NotMatchException;

import static com.sendquiz.global.eumtype.ErrorMessageConstant.*;


public class EmailNotMatchException extends NotMatchException {

    private static final String MESSAGE = MEMBER_NOT_FOUND.message;

    public EmailNotMatchException() {
        super(MESSAGE);
    }
}
