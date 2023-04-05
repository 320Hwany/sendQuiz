package com.sendquiz.member.exception;

import com.sendquiz.global.exception.NotFoundException;

import static com.sendquiz.global.constant.ErrorMessageConstant.MEMBER_NOT_FOUND_MESSAGE;

public class MemberNotFoundException extends NotFoundException {

    private static final String MESSAGE = MEMBER_NOT_FOUND_MESSAGE;

    public MemberNotFoundException() {
        super(MESSAGE);
    }
}
