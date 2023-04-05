package com.sendquiz.global.exception;

import lombok.Getter;

import static com.sendquiz.global.constant.StatusCodeConstant.NOT_MATCH_EXCEPTION;

@Getter
public abstract class NotMatchException extends RuntimeException {

    private final String statusCode = NOT_MATCH_EXCEPTION;

    private String message;

    public NotMatchException(String message) {
        this.message = message;
    }
}
