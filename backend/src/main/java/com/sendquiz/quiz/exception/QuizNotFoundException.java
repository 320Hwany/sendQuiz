package com.sendquiz.quiz.exception;

import com.sendquiz.global.exception.NotFoundException;

import static com.sendquiz.global.eumtype.constant.ErrorMessageConstant.*;

public class QuizNotFoundException extends NotFoundException {

    private static final String MESSAGE = QUIZ_NOT_FOUND.message;

    public QuizNotFoundException() {
        super(MESSAGE);
    }
}
