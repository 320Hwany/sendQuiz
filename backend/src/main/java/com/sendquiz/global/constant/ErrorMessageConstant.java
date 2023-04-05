package com.sendquiz.global.constant;

import lombok.Getter;

@Getter
public class ErrorMessageConstant {

    private ErrorMessageConstant() {
    }

    public static final String MEMBER_DUPLICATION_MESSAGE = "이미 가입된 회원입니다";

    public static final String MEMBER_AUTHENTICATION_MESSAGE = "로그인 후 이용해주세요";

    public static final String MEMBER_NOT_MATCH_MESSAGE = "이메일/비밀번호가 일치하지 않습니다";

    public static final String MEMBER_NOT_FOUND_MESSAGE = "회원을 찾을 수 없습니다";
}
