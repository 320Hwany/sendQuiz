package com.sendquiz.member.domain;

import com.sendquiz.global.eumtype.Role;
import com.sendquiz.member.exception.AdminAuthenticationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AdminSessionTest {

    @Test
    @DisplayName("회원의 관리자가 아니면 예외가 발생합니다")
    void toAdminSessionException() {
        // given
        Member member = Member.builder()
                .email("test@email.com")
                .nickname("test nickname")
                .password("test password")
                .role(Role.BASIC)
                .build();

        // expected
        assertThatThrownBy(() -> AdminSession.toAdminSession(member))
                .isInstanceOf(AdminAuthenticationException.class);
    }

    @Test
    @DisplayName("회원이 관리자이면 AdminSession으로 반환됩니다")
    void toAdminSession() {
        // given
        Member member = Member.builder()
                .email("test@email.com")
                .nickname("test nickname")
                .password("test password")
                .role(Role.ADMIN)
                .build();

        // when
        AdminSession adminSession = AdminSession.toAdminSession(member);

        // then
        assertThat(adminSession.getEmail()).isEqualTo(member.getEmail());
        assertThat(adminSession.getNickname()).isEqualTo(member.getNickname());
        assertThat(adminSession.getPassword()).isEqualTo(member.getPassword());
        assertThat(adminSession.getRole()).isEqualTo(Role.ADMIN);
    }
}