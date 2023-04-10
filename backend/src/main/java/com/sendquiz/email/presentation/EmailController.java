package com.sendquiz.email.presentation;

import com.sendquiz.email.application.EmailCertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EmailController {

    private final EmailCertificationService emailCertificationService;

    @PostMapping("/email/signup")
    public ResponseEntity<Void> emailSignup(@RequestParam("email") String toEmail) {
        emailCertificationService.sendCertificationNum(toEmail);
        return ResponseEntity.ok().build();
    }
}
