package com.sendquiz.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.sendquiz.global.constant.ValidMessageConstant.EMAIL_VALID_MESSAGE;
import static com.sendquiz.global.constant.ValidMessageConstant.PASSWORD_VALID_MESSAGE;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLogin {

    @Email(message = EMAIL_VALID_MESSAGE)
    private String email;

    @Size(min = 6, max = 20, message = PASSWORD_VALID_MESSAGE)
    private String password;

    @Builder
    public MemberLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
