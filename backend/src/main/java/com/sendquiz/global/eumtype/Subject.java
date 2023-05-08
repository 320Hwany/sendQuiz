package com.sendquiz.global.eumtype;

import com.sendquiz.quiz.exception.SubjectNotMatchException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Subject {

    NETWORK("네트워크"),
    DATA_BASE("데이터베이스"),
    OPERATING_SYSTEM("운영체제"),
    DATA_STRUCTURE("자료구조"),
    JAVA("자바"),
    SPRING("스프링");

    private final String value;

    public static Subject fromValue(String value) {
        return Arrays.stream(Subject.values())
                .filter(subject -> subject.value.equals(value))
                .findFirst()
                .orElseThrow(SubjectNotMatchException::new);
    }
}
