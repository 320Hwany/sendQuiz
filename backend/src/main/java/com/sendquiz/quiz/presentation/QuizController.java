package com.sendquiz.quiz.presentation;

import com.sendquiz.global.annotation.AdminLogin;
import com.sendquiz.member.domain.AdminSession;
import com.sendquiz.quiz.application.QuizService;
import com.sendquiz.quiz.dto.request.QuizSave;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/quizList")
    public ResponseEntity<Void> getQuizList() {
        quizService.sendRandomQuizList();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/quiz")
    public ResponseEntity<Void> save(@AdminLogin AdminSession adminSession,
                                     @RequestBody QuizSave quizSave) {
        quizService.save(quizSave);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
