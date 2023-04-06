package com.sendquiz.global.constant;

import lombok.Getter;

@Getter
public class ValidMessageConstant {

    private ValidMessageConstant() {
    }

    public static final String EMAIL_VALID_MESSAGE = "이메일을 입력해주세요";

    public static final String CERTIFICATION_NUM_VALID_MESSAGE = "인증번호를 입력해주세요";

    public static final String NICKNAME_VALID_MESSAGE = "2글자 이상 20글자 이하의 닉네임을 입력해주세요";
}
