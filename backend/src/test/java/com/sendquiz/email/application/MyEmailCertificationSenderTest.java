package com.sendquiz.email.application;

import com.sendquiz.email.application.test.EmailCertificationSenderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MyEmailCertificationSenderTest {

    @InjectMocks
    private EmailCertificationSenderTest emailCertificationSenderTest;

    @Test
    @DisplayName("8자리 UUID를 생성합니다")
    void makeUUID() {
        // when
        String uuid = emailCertificationSenderTest.makeUUID();

        // expected
        assertThat(uuid.length()).isEqualTo(8);
    }
}
