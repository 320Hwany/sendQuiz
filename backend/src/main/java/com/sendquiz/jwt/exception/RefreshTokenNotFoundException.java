package com.sendquiz.jwt.exception;

import com.sendquiz.global.exception.NotFoundException;

import static com.sendquiz.global.constant.ErrorMessageConstant.REFRESH_TOKEN_NOT_FOUND_MESSAGE;

public class RefreshTokenNotFoundException extends NotFoundException {

    private static final String MESSAGE = REFRESH_TOKEN_NOT_FOUND_MESSAGE;

    public RefreshTokenNotFoundException() {
        super(MESSAGE);
    }
}
