package com.sendquiz.global.eumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    BASIC("basic"),
    ADMIN("admin");

    private String value;
}
