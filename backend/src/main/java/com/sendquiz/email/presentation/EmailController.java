package com.sendquiz.email.presentation;

import com.sendquiz.email.application.prod.EmailCertificationSenderProd;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EmailController {

    private final EmailCertificationSenderProd emailCertificationSenderProd;

    @PostMapping("/email/signup")
    public ResponseEntity<Void> emailSignup(@RequestParam("email") String toEmail) {
        emailCertificationSenderProd.sendCertificationNum(toEmail);
        return ResponseEntity.ok().build();
    }
}
