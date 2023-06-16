package com.sendquiz.global.eumtype;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum Role {

    BASIC("BASIC"),
    ADMIN("ADMIN");

    private final String value;

    Role(String value) {
        this.value = value;
    }
}
