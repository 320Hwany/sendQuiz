package com.sendquiz.jwt.exception;

import com.sendquiz.global.exception.NotMatchException;

import static com.sendquiz.global.constant.ErrorMessageConstant.REFRESH_TOKEN_NOT_MATCH_MESSAGE;

public class RefreshTokenNotMatchException extends NotMatchException {

    private static final String MESSAGE = REFRESH_TOKEN_NOT_MATCH_MESSAGE;

    public RefreshTokenNotMatchException() {
        super(MESSAGE);
    }
}
