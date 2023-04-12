package com.sendquiz.global.constant;

import lombok.Getter;

@Getter
public class CommonConstant {

    private CommonConstant() {
    }

    public static final String EMAIL_SUBJECT = "[SendQuiz - CS 면접 질문 리스트 제공 서비스]";

    public static final String EMAIL_SUBJECT_TEST = "테스트 이메일 제목";

    public static final String CERTIFICATION_MESSAGE = "이메일 확인 인증 번호입니다 : ";

    public static final String CERTIFICATION_MESSAGE_TEST = "테스트 이메일 메세지";

    public static final String AUTHORIZATION = "Authorization";

    public static final String REFRESH_TOKEN = "refreshToken";

    public static final Long AFTER_ONE_HOUR = System.currentTimeMillis() + 3600_000L;

    public static final Long AFTER_ONE_MONTH = System.currentTimeMillis() + 2_592_000_000L;
}
