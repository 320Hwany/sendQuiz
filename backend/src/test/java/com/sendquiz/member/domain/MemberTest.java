package com.sendquiz.member.domain;

import com.sendquiz.member.dto.request.MemberUpdate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @DisplayName("회원의 로그인 유지를 위한 refreshToken을 업데이트합니다")
    void updateRefreshToken() {
        // given
        Member member = Member.builder()
                .refreshToken("before update")
                .build();

        // when
        member.updateRefreshToken("after update");

        // then
        assertThat(member.getRefreshToken()).isEqualTo("after update");
    }

    @Test
    @DisplayName("회원의 로그인을 유지를 위한 refreshToken을 삭제합니다")
    void deleteRefreshToken() {
        // given
        Member member = Member.builder()
                .refreshToken("refresh token")
                .build();

        // when
        member.deleteRefreshToken();

        // then
        assertThat(member.getRefreshToken()).isNull();
    }
}