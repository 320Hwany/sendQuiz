package com.sendquiz.quiz_filter.exception;

import com.sendquiz.global.exception.NotFoundException;

import static com.sendquiz.global.eumtype.constant.ErrorMessageConstant.*;

public class QuizFilterNotFoundException extends NotFoundException {

    private static final String MESSAGE = QUIZ_FILTER_NOT_FOUND.message;

    public QuizFilterNotFoundException() {
        super(MESSAGE);
    }
}
