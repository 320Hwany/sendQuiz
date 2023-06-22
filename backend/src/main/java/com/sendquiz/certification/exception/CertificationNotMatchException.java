package com.sendquiz.certification.exception;

import com.sendquiz.global.exception.NotMatchException;

import static com.sendquiz.global.eumtype.constant.ErrorMessageConstant.CERTIFICATION_NOT_MATCH;

public class CertificationNotMatchException extends NotMatchException {

    private static final String MESSAGE = CERTIFICATION_NOT_MATCH.message;

    public CertificationNotMatchException() {
        super(MESSAGE);
    }
}
