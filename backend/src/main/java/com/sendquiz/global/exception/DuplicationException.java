package com.sendquiz.global.exception;


import lombok.Getter;

import static com.sendquiz.global.eumtype.constant.StatusCodeConstant.*;

@Getter
public abstract class DuplicationException extends RuntimeException {

    private final String statusCode = DUPLICATION.statusCode;
    private String message;

    public DuplicationException(String message) {
        this.message = message;
    }
}
