package com.sendquiz.certification.exception;

import com.sendquiz.global.exception.NotFoundException;

import static com.sendquiz.global.constant.ErrorMessageConstant.CACHE_NOT_FOUND_EXCEPTION;

public class CacheNotFoundException extends NotFoundException {

    public static final String MESSAGE = CACHE_NOT_FOUND_EXCEPTION;

    public CacheNotFoundException() {
        super(MESSAGE);
    }
}
