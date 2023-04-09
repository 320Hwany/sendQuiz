package com.sendquiz.global.eumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Subject {

    NETWORK("network"),
    DATA_STRUCTURE("dataStructure"),
    OPERATING_SYSTEM("operatingSystem"),
    DATA_BASE("database"),
    JAVA("java"),
    SPRING("spring");

    private String value;

    public static boolean validateValue(String inputSubject) {
        Subject[] subjectList = values();
        return Arrays.stream(subjectList).anyMatch(subject -> inputSubject.equals(subject.getValue()));
    }
}
