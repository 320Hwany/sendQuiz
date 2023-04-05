package com.sendquiz.member.dto.request;

import com.sendquiz.member.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.sendquiz.global.util.ValidMessageConstant.EMAIL_VALID_MESSAGE;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignup {

    @Email(message = EMAIL_VALID_MESSAGE)
    private String email;

    @Size(min = 6, max = 20)
    private String password;

    @Builder
    public MemberSignup(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }
}
