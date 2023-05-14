package com.sendquiz.quiz.exception;

import com.sendquiz.global.exception.NotFoundException;

import static com.sendquiz.global.constant.ErrorMessageConstant.*;

public class QuizNotFoundException extends NotFoundException {

    private static final String MESSAGE = QUIZ_NOT_FOUND_MESSAGE;

    public QuizNotFoundException() {
        super(MESSAGE);
    }
}
