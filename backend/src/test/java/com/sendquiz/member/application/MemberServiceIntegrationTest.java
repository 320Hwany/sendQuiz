package com.sendquiz.member.application;

import com.sendquiz.certification.repository.CertificationRepository;
import com.sendquiz.jwt.application.response.JwtResponse;
import com.sendquiz.jwt.repository.JwtRepository;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.presentation.request.MemberLogin;
import com.sendquiz.member.repository.MemberRepository;
import com.sendquiz.util.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@AcceptanceTest
public class MemberServiceIntegrationTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    protected JwtRepository jwtRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("로그인에 성공합니다")
    void login200() {
        // given
        MemberLogin memberLogin = MemberLogin.builder()
                .email("test@email.com")
                .password("test password")
                .build();

        Member member = Member.builder()
                .email(memberLogin.getEmail())
                .password(passwordEncoder.encode(memberLogin.getPassword()))
                .nickname("test nickname")
                .build();

        memberRepository.save(member);
        ReflectionTestUtils.setField(member, "id", 1L);

        // when
        JwtResponse jwtResponse = memberService.login(memberLogin);

        // then
        assertThat(jwtResponse.getAccessToken()).isNotBlank();
    }
}
