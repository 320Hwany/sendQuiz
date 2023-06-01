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

    public static final String FROM_EMAIL_ADDRESS = "yhwjd99@gmail.com";

    public static final String ASIA_SEOUL = "Asia/Seoul";

    public static final int ONE_DAY = 86400000;

    public static final long ONE_HOUR = 1000 * 60 * 60;

    public static final long ONE_MONTH = 1000L * 60 * 60 * 24 * 30;

    public static final String NINE_AM = "0 0 9 * * *";

    public static final String EIGHT_AM = "0 0 8 * * *";

    public static final String SEVEN_AM = "0 0 8 * * *";

    public static final String QUIZ_CACHE = "quizCache";

    public static final String QUIZ_LIST = "quizList";

    public static final String UTF_8 = "UTF-8";
}
