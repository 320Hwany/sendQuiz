package com.sendquiz.email.application;


import org.springframework.mail.SimpleMailMessage;

public interface EmailCertificationSender {

    void sendCertificationNum(String toEmail);

    String makeCertificationMessage(String toEmail, SimpleMailMessage message);

    String makeUUID();

    void saveCertificationNum(String toEmail, String certificationNum);
}
