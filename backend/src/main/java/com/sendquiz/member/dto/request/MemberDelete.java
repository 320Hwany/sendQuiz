package com.sendquiz.member.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDelete {

    private String password;

    private String passwordCheck;

    @Builder
    public MemberDelete(String password, String passwordCheck) {
        this.password = password;
        this.passwordCheck = passwordCheck;
    }
}
