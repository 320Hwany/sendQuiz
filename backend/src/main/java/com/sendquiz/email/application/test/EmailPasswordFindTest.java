package com.sendquiz.email.application.test;

import com.sendquiz.email.application.EmailPasswordFind;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.sendquiz.global.eumtype.CommonConstant.MESSAGE_SUBJECT_TEST;

@RequiredArgsConstructor
@Getter
@Service
public class EmailPasswordFindTest implements EmailPasswordFind {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private ThreadLocal<SimpleMailMessage> testMailSender = new ThreadLocal<>();

    @Override
    @Transactional
    public void sendTemporaryPassword(String toEmail) {
        Member member = memberRepository.getByEmail(toEmail);
        SimpleMailMessage message = new SimpleMailMessage();
        String temporaryPassword = makeUUID();
        message.setSubject(MESSAGE_SUBJECT_TEST);
        updateToTemporaryPassword(member, temporaryPassword);
        String context = setContext(temporaryPassword);
        message.setText(context);
        testMailSender.set(message);
    }

    @Override
    public void updateToTemporaryPassword(Member member, String temporaryPassword) {
        member.updateToTemporaryPassword(temporaryPassword, passwordEncoder);
    }

    @Override
    public String makeUUID() {
        return UUID.randomUUID().toString().substring(0,8);
    }

    @Override
    public String setContext(String temporaryPassword) {
        return temporaryPassword;
    }
}
