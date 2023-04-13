package com.sendquiz.member.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    @DisplayName("refreshToken을 업데이트합니다")
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
    @DisplayName("refreshToken을 삭제합니다")
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