package com.sendquiz.global.exception;

import lombok.Getter;

import static com.sendquiz.global.eumtype.StatusCodeConstant.NOT_MATCH_EXCEPTION;

@Getter
public abstract class NotMatchException extends RuntimeException {

    private final String statusCode = NOT_MATCH_EXCEPTION.statusCode;

    private String message;

    public NotMatchException(String message) {
        this.message = message;
    }
}
