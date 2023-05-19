package com.sendquiz.jwt.exception;

import com.sendquiz.global.exception.NotFoundException;

import static com.sendquiz.global.constant.ErrorMessageConstant.COOKIE_NOT_FOUND_MESSAGE;

public class CookieNotFoundException extends NotFoundException {

    private static final String MESSAGE = COOKIE_NOT_FOUND_MESSAGE;

    public CookieNotFoundException() {
        super(MESSAGE);
    }
}
