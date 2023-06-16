package com.sendquiz.email.exception;

import lombok.Getter;

import static com.sendquiz.global.eumtype.ErrorMessageConstant.*;
import static com.sendquiz.global.eumtype.StatusCodeConstant.EMAIL_MESSAGE_EXCEPTION;

@Getter
public class EmailMessageException extends RuntimeException {

    private final String statusCode = EMAIL_MESSAGE_EXCEPTION.statusCode;

    private final String MESSAGE = EMAIL_NOT_SEND.message;
}
