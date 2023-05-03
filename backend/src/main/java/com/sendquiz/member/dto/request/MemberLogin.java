package com.sendquiz.member.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLogin {

    private String email;

    private String password;

    @Builder
    private MemberLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
