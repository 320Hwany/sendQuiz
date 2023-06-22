package com.sendquiz.quiz.exception;

import com.sendquiz.global.exception.NotMatchException;

import static com.sendquiz.global.eumtype.constant.ErrorMessageConstant.*;

public class SubjectNotMatchException extends NotMatchException {

    private static final String MESSAGE = SUBJECT_NOT_MATCH.message;

    public SubjectNotMatchException() {
        super(MESSAGE);
    }
}
