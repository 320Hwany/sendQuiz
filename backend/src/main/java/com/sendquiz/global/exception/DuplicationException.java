package com.sendquiz.global.exception;


import lombok.Getter;

import static com.sendquiz.global.util.StatusCodeConstant.*;

@Getter
public class DuplicationException extends RuntimeException {

    private final String statusCode = DUPLICATION_STATUS_CODE;
    private String message;

    public DuplicationException(String message) {
        this.message = message;
    }
}
