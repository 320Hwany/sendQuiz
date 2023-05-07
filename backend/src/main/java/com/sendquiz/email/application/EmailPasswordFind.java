package com.sendquiz.email.application;

import com.sendquiz.member.domain.Member;

public interface EmailPasswordFind {

    void sendTemporaryPassword(String toEmail);

    void updateToTemporaryPassword(Member member, String temporaryPassword);

    String makeUUID();

    String setContext(String temporaryPassword);
}
