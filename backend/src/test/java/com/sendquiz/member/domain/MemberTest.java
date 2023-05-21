package com.sendquiz.member.domain;

import com.sendquiz.member.presentation.request.MemberUpdate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    PasswordEncoder passwordEncoder = new SCryptPasswordEncoder(16, 8, 1, 32, 64);

    @Test
    @DisplayName("이메일을 제외한 회원 정보를 수정합니다")
    void update() {
        // given
        Member member = Member.builder()
                .email("test@email.com")
                .nickname("test nickname")
                .password("test password")
                .build();

        MemberUpdate memberUpdate = MemberUpdate.builder()
                .nickname("update nickname")
                .password("update password")
                .build();

        // when
        member.update(memberUpdate, passwordEncoder);

        // then
        assertThat(member.getNickname()).isEqualTo("update nickname");
        assertThat(passwordEncoder.matches(memberUpdate.getPassword(), member.getPassword())).isTrue();
     }
}