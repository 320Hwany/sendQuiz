package com.sendquiz.quiz.presentation;

import com.sendquiz.global.annotation.AdminLogin;
import com.sendquiz.member.domain.AdminSession;
import com.sendquiz.quiz.application.QuizQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class QuizTestController {

    private final QuizQuery quizQuery;

    @GetMapping("/quizList")
    public ResponseEntity<Void> getQuizList(@AdminLogin AdminSession adminSession) {
        quizQuery.sendRandomQuizList();
        return ResponseEntity.ok().build();
    }
}
