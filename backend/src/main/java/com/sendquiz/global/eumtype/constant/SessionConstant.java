package com.sendquiz.global.eumtype.constant;

public enum SessionConstant {

    MEMBER_SESSION("MemberSession"),

    ACCESS_TOKEN("Access_token"),

    REFRESH_TOKEN("Refresh_token");

    public final String message;

    SessionConstant(String message) {
        this.message = message;
    }
}
