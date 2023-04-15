package com.sendquiz.quiz_filter.exception;

import com.sendquiz.global.exception.NotFoundException;

import static com.sendquiz.global.constant.ErrorMessageConstant.QUIZ_FILTER_NOT_FOUND_MESSAGE;

public class QuizFilterNotFoundException extends NotFoundException {

    private static final String MESSAGE = QUIZ_FILTER_NOT_FOUND_MESSAGE;

    public QuizFilterNotFoundException() {
        super(MESSAGE);
    }
}
