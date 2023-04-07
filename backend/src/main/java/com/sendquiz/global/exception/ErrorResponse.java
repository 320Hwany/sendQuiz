package com.sendquiz.global.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String statusCode;
    
    private String message;

    @Builder
    public ErrorResponse(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
