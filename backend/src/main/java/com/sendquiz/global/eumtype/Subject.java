package com.sendquiz.global.eumtype;

import lombok.Getter;

@Getter
public enum Subject {

    NETWORK("네트워크"),
    DATA_BASE("데이터베이스"),
    OPERATING_SYSTEM("운영체제"),
    DATA_STRUCTURE("자료구조"),
    JAVA("자바"),
    SPRING("스프링");

    private final String value;

    Subject(String value) {
        this.value = value;
    }
}
