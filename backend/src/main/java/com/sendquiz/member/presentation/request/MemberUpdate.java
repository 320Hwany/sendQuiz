package com.sendquiz.member.presentation.request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.sendquiz.global.constant.ValidMessageConstant.NICKNAME_VALID_MESSAGE;
import static com.sendquiz.global.constant.ValidMessageConstant.PASSWORD_VALID_MESSAGE;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdate {

    @Size(min = 2, max = 16, message = NICKNAME_VALID_MESSAGE)
    private String nickname;

    @Size(min = 6, max = 20, message = PASSWORD_VALID_MESSAGE)
    private String password;

    @Builder
    private MemberUpdate(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}
