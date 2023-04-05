package com.sendquiz.global.exception;

import lombok.Getter;

import static com.sendquiz.global.constant.StatusCodeConstant.NOTFOUND_STATUS_CODE;

@Getter
public abstract class NotFoundException extends RuntimeException{

    private final String statusCode = NOTFOUND_STATUS_CODE;

    private String message;

    public NotFoundException(String message) {
        this.message = message;
    }
}
