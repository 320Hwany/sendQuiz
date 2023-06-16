package com.sendquiz.global.eumtype;

public enum StatusCodeConstant {
    VALID_BAD_REQUEST("400"),
    DUPLICATION("400"),
    NOT_MATCH_EXCEPTION("400"),
    EMAIL_MESSAGE_EXCEPTION("400"),
    AUTHENTICATION("401"),
    NOTFOUND("404");

    public final String statusCode;

    StatusCodeConstant(String statusCode) {
        this.statusCode = statusCode;
    }
}

