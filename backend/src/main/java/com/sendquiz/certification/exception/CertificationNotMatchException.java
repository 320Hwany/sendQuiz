package com.sendquiz.certification.exception;

import com.sendquiz.global.exception.NotMatchException;

import static com.sendquiz.global.constant.ErrorMessageConstant.CERTIFICATION_NOT_MATCH_MESSAGE;

public class CertificationNotMatchException extends NotMatchException {

    private static final String MESSAGE = CERTIFICATION_NOT_MATCH_MESSAGE;

    public CertificationNotMatchException() {
        super(MESSAGE);
    }
}
