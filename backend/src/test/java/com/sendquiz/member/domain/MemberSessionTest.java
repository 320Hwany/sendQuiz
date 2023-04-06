package com.sendquiz.member.domain;

import jakarta.servlet.http.HttpSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static com.sendquiz.global.constant.CommonConstant.MEMBER_SESSION;
import static org.assertj.core.api.Assertions.*;

class MemberSessionTest {

    @Test
    @DisplayName("해당 브라우저의 세션을 생성합니다")
    void makeSession() {
        // given
        MemberSession memberSession = MemberSession.builder()
                .id(1L)
                .email("test@email.com")
                .nickname("test nickname")
                .password("test password")
                .numOfProblem(5)
                .build();

        MockHttpServletRequest request = new MockHttpServletRequest();

        // when
        memberSession.makeSession(request);

        // then
        HttpSession session = request.getSession(false);
        MemberSession findMemberSession = (MemberSession) session.getAttribute(MEMBER_SESSION);
        assertThat(findMemberSession).isNotNull();
    }

    @Test
    @DisplayName("해당 브라우저의 세션을 삭제합니다")
    void invalidate() {
        // given
        MemberSession memberSession = MemberSession.builder()
                .id(1L)
                .email("test@email.com")
                .nickname("test nickname")
                .password("test password")
                .numOfProblem(5)
                .build();

        MockHttpServletRequest request = new MockHttpServletRequest();
        memberSession.makeSession(request);

        // when
        memberSession.invalidate(request);

        // then
        HttpSession session = request.getSession(false);
        assertThat(session).isNull();
    }
}