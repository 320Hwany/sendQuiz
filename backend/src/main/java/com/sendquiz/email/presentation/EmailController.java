package com.sendquiz.email.presentation;

import com.sendquiz.email.application.EmailCertificationSender;
import com.sendquiz.email.application.EmailPasswordFind;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EmailController {

    private final EmailCertificationSender emailCertificationSender;
    private final EmailPasswordFind emailPasswordFind;

    @PostMapping("/email/signup")
    public ResponseEntity<Void> emailSignup(@RequestParam("email") String toEmail) {
        emailCertificationSender.sendCertificationNum(toEmail);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/email/password")
    public ResponseEntity<Void> emailPasswordFind(@RequestParam("email") String toEmail) {
        emailPasswordFind.sendTemporaryPassword(toEmail);
        return ResponseEntity.ok().build();
    }
}
