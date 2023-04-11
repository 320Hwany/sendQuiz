package com.sendquiz.email.application;

import com.sendquiz.certification.domain.Certification;
import com.sendquiz.certification.repository.CertificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;

import java.util.Optional;

import static com.sendquiz.global.constant.CommonConstant.CERTIFICATION_MESSAGE_TEST;
import static com.sendquiz.global.constant.CommonConstant.EMAIL_SUBJECT_TEST;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MyEmailCertificationSenderTest {

    @InjectMocks
    private EmailCertificationSenderTest emailCertificationSenderTest;

    @Mock
    private CertificationRepository certificationRepository;

    @Test
    @DisplayName("인증번호에 대한 메일을 생성하고 인증번호를 반환합니다")
    void makeCertificationMessage() {
        // given
        String toMail = "test email";
        SimpleMailMessage message = new SimpleMailMessage();

        // when
        String certificationNum = emailCertificationSenderTest.makeCertificationMessage(toMail, message);

        // then
        assertThat(message.getSubject()).isEqualTo(EMAIL_SUBJECT_TEST);
        assertThat(message.getText()).isEqualTo(CERTIFICATION_MESSAGE_TEST);
        assertThat(certificationNum.length()).isEqualTo(8);
    }

    @Test
    @DisplayName("8자리 UUID를 생성합니다")
    void makeUUID() {
        // when
        String uuid = emailCertificationSenderTest.makeUUID();

        // expected
        assertThat(uuid.length()).isEqualTo(8);
    }

    @Test
    @DisplayName("해당 이메일에 대한 인증번호가 존재하지 않으면 저장합니다")
    void saveCertificationNum() {
        // given
        String toMail = "test email";
        String certificationNum = "test certification num";

        // stub
        when(certificationRepository.findByEmail(any())).thenReturn(Optional.empty());

        // when
        emailCertificationSenderTest.saveCertificationNum(toMail, certificationNum);

        // then
        verify(certificationRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("해당 이메일에 대한 인증번호가 존재하면 업데이트합니다")
    void updateCertificationNum() {
        // given
        String toMail = "test email";
        String certificationNum = "after update num";
        Certification certification = Certification.builder()
                .certificationNum("before update num")
                .build();

        // stub
        when(certificationRepository.findByEmail(any())).thenReturn(Optional.ofNullable(certification));
        when(certificationRepository.getByEmail(any())).thenReturn(certification);

        // when
        emailCertificationSenderTest.saveCertificationNum(toMail, certificationNum);

        // then
        assert certification != null;
        assertThat(certification.getCertificationNum()).isEqualTo("after update num");
    }
}
