package com.sendquiz.email.application.prod;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.sendquiz.email.application.EmailPasswordFind;
import com.sendquiz.email.exception.EmailMessageException;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.UUID;

import static com.sendquiz.global.constant.CommonConstant.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Primary
@Service
public class EmailPasswordFindProd implements EmailPasswordFind {

    private final AmazonSimpleEmailService amazonSimpleEmailService;
    private final SpringTemplateEngine templateEngine;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void sendTemporaryPassword(String toEmail) {
        Member member = memberRepository.getByEmail(toEmail);
        String temporaryPassword = makeUUID();
        updateToTemporaryPassword(member, temporaryPassword);

        SendEmailRequest emailRequest = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(toEmail))
                .withMessage(new Message()
                        .withSubject(new Content().withData(EMAIL_SUBJECT))
                        .withBody(new Body().withHtml(new Content().withData(setContext(temporaryPassword))))
                )
                .withSource(FROM_EMAIL_ADDRESS);

        try {
            amazonSimpleEmailService.sendEmail(emailRequest);
        } catch (AmazonClientException e) {
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

