package com.sendquiz.global.constant;

import lombok.Getter;

@Getter
public class CommonConstant {

    private CommonConstant() {
    }

    public static final String EMAIL_SUBJECT = "[SendQuiz - CS 면접 질문 리스트 제공 서비스]";

    public static final String EMAIL_SUBJECT_TEST = "테스트 이메일 제목";

    public static final String CERTIFICATION_MESSAGE = "이메일 확인 인증 번호입니다 : ";

    public static final String MESSAGE_SUBJECT_TEST = "테스트 이메일 제목";

    public static final String CERTIFICATION_NUM = "certificationNum";

    public static final String CERTIFICATION = "certification";

    public static final String TEMPORARY_PASSWORD = "temporaryPassword";

    public static final String ACCESS_TOKEN = "Access_token";

    public static final String REFRESH_TOKEN = "Refresh_token";

    public static final String SAME_SITE_NONE = "None";

    public static final String SERVER_DOMAIN = ".send-quiz.store";

    public static final Long AFTER_ONE_HOUR = System.currentTimeMillis() + 3600_000L;

    public static final Long AFTER_ONE_MONTH = System.currentTimeMillis() + 2_592_000_000L;

    public static final int ONE_DAY = 86400000;

    public static final String SEVEN_AM = "0 0 7 * * *";

    public static final String QUIZ_CACHE = "quizCache";

    public static final String QUIZ_LIST = "quizList";

    public static final String UTF_8 = "UTF-8";
}
