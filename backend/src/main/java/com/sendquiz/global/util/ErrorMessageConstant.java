package com.sendquiz.global.util;

import lombok.Getter;

@Getter
public class ErrorMessageConstant {

    private ErrorMessageConstant() {
    }

    public static final String MEMBER_DUPLICATION_MESSAGE = "이미 가입된 회원입니다";
}
