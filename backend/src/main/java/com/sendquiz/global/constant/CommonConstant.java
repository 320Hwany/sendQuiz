package com.sendquiz.global.constant;

import lombok.Getter;

@Getter
public class CommonConstant {

    private CommonConstant() {
    }

    public static final String EMAIL_SUBJECT = "[SendQuiz - CS 면접 질문 리스트 제공 서비스]";

    public static final String CERTIFICATION_MESSAGE = "이메일 확인 인증 번호입니다 : ";
}
