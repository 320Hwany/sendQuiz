package com.sendquiz.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.sendquiz.global.constant.ErrorMessageConstant.EMAIL_NOT_FOUND_MESSAGE;
import static com.sendquiz.global.constant.StatusCodeConstant.VALID_BAD_REQUEST_STATUS_CODE;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MethodArgumentErrorResponse> methodArgumentNotValidException(
                                MethodArgumentNotValidException e) {
        MethodArgumentErrorResponse errorResponse = MethodArgumentErrorResponse.builder()
                .statusCode(VALID_BAD_REQUEST_STATUS_CODE)
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            errorResponse.addValidation(fieldError);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DuplicationException.class)
    public ResponseEntity<ErrorResponse> duplicationException(DuplicationException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(e.getStatusCode())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NotMatchException.class)
    public ResponseEntity<ErrorResponse> notMatchException(NotMatchException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(e.getStatusCode())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> authenticationException(AuthenticationException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(e.getStatusCode())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException(NotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(e.getStatusCode())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<ErrorResponse> mailSendException(MailSendException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(VALID_BAD_REQUEST_STATUS_CODE)
                .message(EMAIL_NOT_FOUND_MESSAGE)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
