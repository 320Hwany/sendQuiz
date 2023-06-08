package com.sendquiz.member.dto.request;

import com.sendquiz.global.eumtype.Role;
import com.sendquiz.member.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.sendquiz.global.constant.ValidMessageConstant.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignup {

    @NotBlank(message = EMAIL_VALID_MESSAGE)
    @Email(message = EMAIL_VALID_MESSAGE)
    private String email;

    @NotBlank(message = CERTIFICATION_NUM_VALID_MESSAGE)
    private String certificationNum;

    @Size(min = 2, max = 16, message = NICKNAME_VALID_MESSAGE)
    private String nickname;

    @Size(min = 6, max = 20, message = PASSWORD_VALID_MESSAGE)
    private String password;

    @Builder
    private MemberSignup(String email, String certificationNum, String nickname, String password) {
        this.email = email;
        this.certificationNum = certificationNum;
        this.nickname = nickname;
        this.password = password;
    }

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .password(passwordEncoder.encode(password))
                .role(Role.BASIC)
                .build();
    }
}
