package com.sendquiz.member.exception;

import com.sendquiz.global.exception.NotMatchException;

import static com.sendquiz.global.eumtype.ErrorMessageConstant.*;

public class PasswordNotMatchException extends NotMatchException {

    public static final String MESSAGE = PASSWORD_NOT_MATCH.message;

    public PasswordNotMatchException() {
        super(MESSAGE);
    }
}
