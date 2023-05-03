package com.sendquiz.global.eumtype;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    BASIC("BASIC"),
    ADMIN("ADMIN");

    private final String value;
}
