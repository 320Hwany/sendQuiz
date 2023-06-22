package com.sendquiz.jwt.exception;

import com.sendquiz.global.exception.NotMatchException;

import static com.sendquiz.global.eumtype.constant.ErrorMessageConstant.*;

public class RefreshTokenNotMatchException extends NotMatchException {

    private static final String MESSAGE = REFRESH_TOKEN_NOT_MATCH.message;

    public RefreshTokenNotMatchException() {
        super(MESSAGE);
    }
}
