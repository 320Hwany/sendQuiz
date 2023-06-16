package com.sendquiz.email.application.prod;

import com.sendquiz.email.application.EmailPasswordFind;
import com.sendquiz.email.exception.EmailMessageException;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.UUID;

import static com.sendquiz.global.eumtype.CommonConstant.*;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Primary
@Service
public class EmailPasswordFindProd implements EmailPasswordFind {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void sendTemporaryPassword(String toEmail) {
        Member member = memberRepository.getByEmail(toEmail);
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8);
            String temporaryPassword = makeUUID();
            updateToTemporaryPassword(member, temporaryPassword);
            helper.setTo(toEmail);
            helper.setSubject(EMAIL_SUBJECT);
            helper.setText(setContext(temporaryPassword), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error Message={}", e.getMessage());
            throw new EmailMessageException();
        }
    }

    @Override
    public String makeUUID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    @Transactional
    public void updateToTemporaryPassword(Member member, String temporaryPassword) {
        member.updateToTemporaryPassword(temporaryPassword, passwordEncoder);
    }

    @Override
    public String setContext(String temporaryPassword) {
        Context context = new Context();
        context.setVariable(TEMPORARY_PASSWORD, temporaryPassword);
        return templateEngine.process(TEMPORARY_PASSWORD, context);
    }
}

