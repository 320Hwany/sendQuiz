package com.sendquiz.email.application;


public interface EmailCertificationSender {

    void sendCertificationNum(String toEmail);

    String setContext(String certificationNum);

    String makeUUID();
}
