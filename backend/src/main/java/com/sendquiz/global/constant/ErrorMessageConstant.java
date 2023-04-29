package com.sendquiz.global.constant;

import lombok.Getter;

@Getter
public class ErrorMessageConstant {

    private ErrorMessageConstant() {
    }

    public static final String MEMBER_DUPLICATION_MESSAGE = "이미 가입된 회원입니다";

    public static final String MEMBER_AUTHENTICATION_MESSAGE = "로그인 후 이용해주세요";

    public static final String ADMIN_AUTHENTICATION_MESSAGE = "관리자로 로그인 후 이용해주세요";

    public static final String MEMBER_NOT_MATCH_MESSAGE = "이메일/비밀번호가 일치하지 않습니다";

    public static final String PASSWORD_NOT_MATCH_MESSAGE = "비밀번호가 일치하지 않습니다";

    public static final String MEMBER_NOT_FOUND_MESSAGE = "해당 이메일로 회원을 찾을 수 없습니다";

    public static final String CERTIFICATION_NOT_MATCH_MESSAGE = "인증번호가 일치하지 않습니다";

    public static final String SUBJECT_NOT_MATCH_MESSAGE = "해당 과목에 대한 요청을 처리할 수 없습니다";

    public static final String VALID_BAD_REQUEST_MESSAGE = "잘못된 요청입니다";

    public static final String EMAIL_NOT_FOUND_MESSAGE = "유효하지 않은 이메일 주소입니다";

    public static final String QUIZ_FILTER_NOT_FOUND_MESSAGE = "아직 이용하고 있는 서비스가 없습니다";
}
