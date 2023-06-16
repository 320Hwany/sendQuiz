package com.sendquiz.jwt.exception;

import com.sendquiz.global.exception.NotMatchException;

import static com.sendquiz.global.eumtype.ErrorMessageConstant.JWS_NOT_MATCH;

public class JwsNotMatchException extends NotMatchException {

    private static final String MESSAGE = JWS_NOT_MATCH.message;

    public JwsNotMatchException() {
        super(MESSAGE);
    }
}
