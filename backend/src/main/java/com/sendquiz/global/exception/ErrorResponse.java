package com.sendquiz.global.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String statusCode;
    private String message;

    private Map<String, String> validation = new ConcurrentHashMap<>();

    @Builder
    public ErrorResponse(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public void addValidation(FieldError fieldError) {
        validation.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
