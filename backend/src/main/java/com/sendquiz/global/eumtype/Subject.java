package com.sendquiz.global.eumtype;

import com.sendquiz.quiz.exception.SubjectNotMatchException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Subject {

    NETWORK("네트워크"),
    DATA_BASE("데이터베이스"),
    OPERATING_SYSTEM("운영체제"),
    DATA_STRUCTURE("자료구조"),
    JAVA("자바"),
    SPRING("스프링");

    private String value;

    public static Subject fromValue(String value) {
        for (Subject subject : Subject.values()) {
            if (subject.value.equals(value)) {
                return subject;
            }
        }
        throw new SubjectNotMatchException();
    }
}
