package com.sendquiz.member.application;

import com.sendquiz.certification.application.CertificationService;
import com.sendquiz.certification.exception.CertificationNotMatchException;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.exception.MemberDuplicationException;
import com.sendquiz.member.dto.request.MemberSignup;
import com.sendquiz.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberQueryTest {

    @InjectMocks
    private MemberQuery memberQuery;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CertificationService certificationService;

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
        memberQuery.validateDuplicate(memberSignup);
    }

    @Test
    @DisplayName("이미 가입된 회원이면 예외가 발생합니다")
    void validateDuplicate400() {
        // given
        SCryptPasswordEncoder passwordEncoder =
                new SCryptPasswordEncoder(16, 8, 1, 32, 64);
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
        assertThatThrownBy(() -> memberQuery.validateDuplicate(memberSignup))
                .isInstanceOf(MemberDuplicationException.class);
    }

    @Test
    @DisplayName("회원가입시 입력한 인증번호가 캐시에 저장된 인증번호와 일치하면 메소드를 통과합니다")
    void validateCertificationNum() {
        // given
        MemberSignup memberSignup = MemberSignup.builder()
                .email("test email")
                .certificationNum("test certification number")
                .build();

        // stub
        when(certificationService.getCertificationNumFromCache(anyString()))
                .thenReturn("test certification number");

        // when
        memberQuery.validateCertificationNum(memberSignup);
    }

    @Test
    @DisplayName("회원가입시 입력한 인증번호가 캐시에 저장된 인증번호와 일치하지 않으면 예외가 발생합니다")
    void validateCertificationNumFail() {
        // given
        MemberSignup memberSignup = MemberSignup.builder()
                .email("test email")
                .certificationNum("test certification number")
                .build();

        // stub
        when(certificationService.getCertificationNumFromCache(anyString()))
                .thenReturn("일치하지 않는 인증번호");

        // expected
        assertThatThrownBy(() -> memberQuery.validateCertificationNum(memberSignup))
                .isInstanceOf(CertificationNotMatchException.class);
    }
}
