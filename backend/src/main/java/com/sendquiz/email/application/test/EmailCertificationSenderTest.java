package com.sendquiz.email.application.test;

import com.sendquiz.email.application.EmailCertificationSender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.sendquiz.global.constant.CommonConstant.*;

@RequiredArgsConstructor
@Getter
@Service
public class EmailCertificationSenderTest implements EmailCertificationSender {

    private ThreadLocal<SimpleMailMessage> testMailSender = new ThreadLocal<>();

    @Transactional
    public void sendCertificationNum(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        String certificationNum = makeUUID();
        String context = setContext(certificationNum);
        message.setText(context);
        testMailSender.set(message);
    }

    @Override
    public String setContext(String certificationNum) {
        return MESSAGE_SUBJECT_TEST;
    }

    public String makeUUID() {
        return UUID.randomUUID().toString().substring(0,8);
    }
}
