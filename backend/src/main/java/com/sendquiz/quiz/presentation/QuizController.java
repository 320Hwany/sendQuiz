package com.sendquiz.quiz.presentation;

import com.sendquiz.global.annotation.AdminLogin;
import com.sendquiz.member.domain.AdminSession;
import com.sendquiz.quiz.application.QuizService;
import com.sendquiz.quiz.presentation.request.QuizSave;
import com.sendquiz.quiz.presentation.request.QuizUpdate;
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

    @PatchMapping("/quiz")
    public ResponseEntity<Void> update(@AdminLogin AdminSession adminSession,
                                       @RequestBody QuizUpdate quizUpdate) {
        quizService.update(quizUpdate);
        return ResponseEntity.ok().build();
    }
}
