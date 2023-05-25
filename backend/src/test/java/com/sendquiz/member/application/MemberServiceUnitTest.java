package com.sendquiz.member.application;

import com.sendquiz.certification.domain.Certification;
import com.sendquiz.certification.exception.CertificationNotMatchException;
import com.sendquiz.certification.repository.CertificationRepository;
import com.sendquiz.jwt.domain.JwtRefreshToken;
import com.sendquiz.jwt.repository.JwtRepository;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.member.presentation.request.MemberDelete;
import com.sendquiz.member.presentation.request.MemberLogin;
import com.sendquiz.member.presentation.request.MemberSignup;
import com.sendquiz.member.presentation.request.MemberUpdate;
import com.sendquiz.member.exception.MemberDuplicationException;
import com.sendquiz.member.exception.MemberNotMatchException;
import com.sendquiz.member.exception.PasswordNotMatchException;
import com.sendquiz.member.repository.MemberRepository;
import com.sendquiz.quiz_filter.domain.QuizFilter;
import com.sendquiz.quiz_filter.repository.QuizFilterRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceUnitTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CertificationRepository certificationRepository;

    @Mock
    private QuizFilterRepository quizFilterRepository;

    @Mock
    private JwtRepository jwtRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    @DisplayName("회원가입에 성공합니다")
    void signup() {
        // given
        MemberSignup memberSignup = MemberSignup.builder()
                .email("test@email.com")
                .certificationNum("abcdefgh")
                .nickname("test nickname")
                .password("test password")
                .build();

        Certification certification = Certification.builder()
                .email(memberSignup.getEmail())
                .certificationNum(memberSignup.getCertificationNum())
                .build();

        // stub
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(certificationRepository.getByEmail(memberSignup.getEmail())).thenReturn(certification);

        // when
        memberService.signup(memberSignup);

        // then
        verify(memberRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("신규 회원이면 메소드를 통과합니다")
    void validateDuplicate200() {
        // given
        MemberSignup memberSignup = MemberSignup.builder()
                .email("test@email.com")
                .nickname("test nickname")
                .password("test password")
                .build();

        // stub
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // then
        memberService.validateDuplicate(memberSignup);
    }

    @Test
    @DisplayName("이미 가입된 회원이면 예외가 발생합니다")
    void validateDuplicate400() {
        // given
        MemberSignup memberSignup = MemberSignup.builder()
                .email("test@email.com")
                .nickname("test nickname")
                .password("test password")
                .build();

        Member member = memberSignup.toEntity(passwordEncoder);

        // stub
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));

        // then
        assertThatThrownBy(() -> memberService.validateDuplicate(memberSignup))
                .isInstanceOf(MemberDuplicationException.class);
    }

    @Test
    @DisplayName("인증번호가 일치하면 메소드를 통과합니다")
    void validateCertificationNum200() {
        // given
        MemberSignup memberSignup = MemberSignup.builder()
                .email("test@email.com")
                .certificationNum("abcdefgh")
                .nickname("test nickname")
                .password("test password")
                .build();

        Certification certification = Certification.builder()
                .email(memberSignup.getEmail())
                .certificationNum(memberSignup.getCertificationNum())
                .build();

        // stub
        when(certificationRepository.getByEmail(memberSignup.getEmail())).thenReturn(certification);

        // when
        memberService.validateCertificationNum(memberSignup);
    }

    @Test
    @DisplayName("인증번호가 일치하지 않으면 예외가 발생합니다")
    void validateCertificationNum400() {
        // given
        MemberSignup memberSignup = MemberSignup.builder()
                .email("test@email.com")
                .certificationNum("abcdefgh")
                .nickname("test nickname")
                .password("test password")
                .build();

        Certification certification = Certification.builder()
                .email(memberSignup.getEmail())
                .certificationNum("일치하지 않는 인증번호")
                .build();

        // stub
        when(certificationRepository.getByEmail(memberSignup.getEmail())).thenReturn(certification);

        // when
        assertThatThrownBy(() -> memberService.validateCertificationNum(memberSignup))
                .isInstanceOf(CertificationNotMatchException.class);
    }

    @Test
    @DisplayName("이메일과 비밀번호가 일치하지 않으면 예외가 발생합니다")
    void login400() {
        // given
        MemberLogin memberLogin = MemberLogin.builder()
                .email("test@email.com")
                .password("test password")
                .build();

        Member member = Member.builder()
                .email(memberLogin.getEmail())
                .password(memberLogin.getPassword())
                .nickname("test nickname")
                .build();

        ReflectionTestUtils.setField(member, "id", 1L);

        // stub
        when(memberRepository.getByEmail(anyString())).thenReturn(member);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // then
        assertThatThrownBy(() -> memberService.login(memberLogin, new MockHttpServletResponse()))
                .isInstanceOf(MemberNotMatchException.class);
    }

    @Test
    @DisplayName("로그아웃을 하면 회원의 refreshToken이 삭제됩니다")
    void logout() {
        // given
        MemberSession memberSession = MemberSession.builder().build();

        JwtRefreshToken refreshToken = JwtRefreshToken.builder()
                .refreshToken("refresh token")
                .build();

        // stub
        when(jwtRepository.getByMemberId(any())).thenReturn(refreshToken);

        // when
        memberService.logout(memberSession);

        // then
        verify(jwtRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("비밀번호가 일치하면 회원을 삭제합니다")
    void delete() {
        // stub
        when(memberRepository.getById(any())).thenReturn(Member.builder().build());
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        // when
        memberService.delete(MemberSession.builder().build(), MemberDelete.builder().build());

        // then
        verify(memberRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 예외가 발생합니다")
    void deleteFail() {
        // stub
        when(memberRepository.getById(any())).thenReturn(Member.builder().build());
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        // expected
        assertThatThrownBy(() ->
                memberService.delete(MemberSession.builder().build(), MemberDelete.builder().build()))
                .isInstanceOf(PasswordNotMatchException.class);
    }

    @Test
    @DisplayName("회원의 퀴즈 필터가 등록되어 있으면 퀴즈 필터도 같이 삭제됩니다")
    void deleteWithQuizFilter() {
        // stub
        when(memberRepository.getById(any())).thenReturn(Member.builder().build());
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(quizFilterRepository.findByMemberId(any()))
                .thenReturn(Optional.ofNullable(QuizFilter.builder().build()));

        // when
        memberService.delete(MemberSession.builder().build(), MemberDelete.builder().build());

        // then
        verify(quizFilterRepository, times(1)).delete(any());
        verify(memberRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("이메일을 제외한 회원 정보를 수정합니다")
    void update() {
        // given
        Member member = Member.builder()
                .nickname("test nickname")
                .password("test password")
                .build();

        MemberUpdate memberUpdate = MemberUpdate.builder()
                .nickname("update nickname")
                .password("update password")
                .build();

        // stub
        when(memberRepository.getById(any())).thenReturn(member);
        when(passwordEncoder.encode(any())).thenReturn("encode password");

        // when
        memberService.update(MemberSession.builder().build(), memberUpdate);

        // then
        assertThat(member)
                .extracting("nickname", "password")
                .contains("update nickname", "encode password");
    }
}