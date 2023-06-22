package com.sendquiz.member.exception;

import com.sendquiz.global.exception.NotFoundException;

import static com.sendquiz.global.eumtype.constant.ErrorMessageConstant.*;


public class MemberNotFoundException extends NotFoundException {

    private static final String MESSAGE = MEMBER_NOT_FOUND.message;

    public MemberNotFoundException() {
        super(MESSAGE);
    }
}
