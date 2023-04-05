package com.sendquiz.member.dto.request;

import com.sendquiz.member.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.sendquiz.global.constant.ValidMessageConstant.EMAIL_VALID_MESSAGE;
import static com.sendquiz.global.constant.ValidMessageConstant.NICKNAME_VALID_MESSAGE;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignup {

    @Email(message = EMAIL_VALID_MESSAGE)
    private String email;

    @Size(min = 2, max = 20, message = NICKNAME_VALID_MESSAGE)
    private String nickname;

    @Size(min = 6, max = 20)
    private String password;

    @Builder
    public MemberSignup(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
