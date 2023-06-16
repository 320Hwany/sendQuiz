package com.sendquiz.global.exception;

import lombok.Getter;

import static com.sendquiz.global.eumtype.StatusCodeConstant.NOTFOUND;

@Getter
public abstract class NotFoundException extends RuntimeException{

    private final String statusCode = NOTFOUND.statusCode;

    private String message;

    public NotFoundException(String message) {
        this.message = message;
    }
}
