package com.sendquiz.jwt.exception;

import com.sendquiz.global.exception.NotMatchException;

import static com.sendquiz.global.constant.ErrorMessageConstant.JWS_NOT_MATCH_MESSAGE;

public class JwsNotMatchException extends NotMatchException {

    private static final String MESSAGE = JWS_NOT_MATCH_MESSAGE;

    public JwsNotMatchException() {
        super(MESSAGE);
    }
}
