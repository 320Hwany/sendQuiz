package com.sendquiz.email.exception;

import lombok.Getter;

import static com.sendquiz.global.constant.ErrorMessageConstant.EMAIL_NOT_SEND_MESSAGE;
import static com.sendquiz.global.constant.StatusCodeConstant.EMAIL_MESSAGE_EXCEPTION;

@Getter
public class EmailMessageError extends RuntimeException {

    private final String statusCode = EMAIL_MESSAGE_EXCEPTION;

    private final String MESSAGE = EMAIL_NOT_SEND_MESSAGE;
}
