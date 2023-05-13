package com.sendquiz.quiz.presentation;

import com.sendquiz.global.annotation.AdminLogin;
import com.sendquiz.member.domain.AdminSession;
import com.sendquiz.quiz.application.QuizService;
import com.sendquiz.quiz.presentation.request.QuizSave;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
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

    @GetMapping("/test")
    public void test() {
        quizService.sendRandomQuizList();
    }
}
