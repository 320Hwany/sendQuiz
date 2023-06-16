package com.sendquiz.global.eumtype;


public enum ErrorMessageConstant {

    MEMBER_DUPLICATION("이미 가입된 회원입니다"),
    MEMBER_AUTHENTICATION("로그인 후 이용해주세요"),
    COOKIE_EXPIRED("쿠키가 만료되었습니다"),
    ADMIN_AUTHENTICATION("관리자로 로그인 후 이용해주세요"),
    MEMBER_NOT_MATCH("이메일/비밀번호가 일치하지 않습니다"),
    PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다"),
    MEMBER_NOT_FOUND("해당 이메일로 회원을 찾을 수 없습니다"),
    CERTIFICATION_NOT_MATCH("인증번호가 일치하지 않습니다"),
    REFRESH_TOKEN_NOT_MATCH("Refresh Token이 일치하지 않습니다"),
    JWS_NOT_MATCH("JWS 정보를 확인해주세요"),
    ACCESS_TOKEN_AUTHENTICATION("AccessToken이 없어 로그인할 수 없습니다"),
    REFRESH_TOKEN_AUTHENTICATION("RefreshToken이 없어 로그인할 수 없습니다"),
    SUBJECT_NOT_MATCH("해당 과목에 대한 요청을 처리할 수 없습니다"),
    EMAIL_NOT_FOUND("유효하지 않은 이메일 주소입니다"),
    QUIZ_NOT_FOUND("퀴즈를 찾을 수 없습니다"),
    QUIZ_FILTER_NOT_FOUND("아직 이용하고 있는 서비스가 없습니다"),
    EMAIL_NOT_SEND("이메일을 전송할 수 없습니다"),
    CACHE_NOT_FOUND_EXCEPTION("캐시를 찾을 수 없습니다");

    public final String message;

    ErrorMessageConstant(String message) {
        this.message = message;
    }
}
