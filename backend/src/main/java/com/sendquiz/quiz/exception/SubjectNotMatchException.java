package com.sendquiz.quiz.exception;

import com.sendquiz.global.exception.NotMatchException;

import static com.sendquiz.global.constant.ErrorMessageConstant.SUBJECT_NOT_MATCH_MESSAGE;

public class SubjectNotMatchException extends NotMatchException {

    private static final String MESSAGE = SUBJECT_NOT_MATCH_MESSAGE;

    public SubjectNotMatchException() {
        super(MESSAGE);
    }
}
