package com.sendquiz.global.eumtype.constant;

public enum EmailConstant {

    EMAIL_SUBJECT("[SendQuiz - CS 면접 질문 리스트 제공 서비스]"),

    EMAIL_SUBJECT_TEST("테스트 이메일 제목"),

    MESSAGE_SUBJECT_TEST("테스트 이메일 제목"),

    CERTIFICATION_NUM("certificationNum"),

    CERTIFICATION("certification"),

    TEMPORARY_PASSWORD("temporaryPassword");

    public final String message;

    EmailConstant(String message) {
        this.message = message;
    }
}
