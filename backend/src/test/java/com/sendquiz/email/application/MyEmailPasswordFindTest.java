package com.sendquiz.email.application;

import com.sendquiz.email.application.test.EmailPasswordFindTest;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.sendquiz.global.eumtype.CommonConstant.MESSAGE_SUBJECT_TEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MyEmailPasswordFindTest {

    @InjectMocks
    private EmailPasswordFindTest emailPasswordFindTest;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Test
    @DisplayName("임시 비밀번호에 대한 메일이 전송됩니다")
    void sendTemporaryPassword() {
        // given
        Member member = Member.builder()
                .password("before update")
                .build();

        String toMail = "test email";
        
        // stub
        when(memberRepository.getByEmail(toMail)).thenReturn(member);
        when(passwordEncoder.encode(any())).thenReturn("after update");

        // when
        emailPasswordFindTest.sendTemporaryPassword(toMail);

        // then
        ThreadLocal<SimpleMailMessage> testMailSender = emailPasswordFindTest.getTestMailSender();
        SimpleMailMessage message = testMailSender.get();
        assertThat(message.getSubject()).isEqualTo(MESSAGE_SUBJECT_TEST);
        assertThat(member.getPassword()).isEqualTo("after update");
    }
}