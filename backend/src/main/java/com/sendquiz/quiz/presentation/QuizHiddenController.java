package com.sendquiz.quiz.presentation;

import com.sendquiz.quiz.application.QuizQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class QuizHiddenController {

    private final QuizQuery quizQuery;

    @GetMapping("/quizList")
    public ResponseEntity<Void> getQuizList() {
        quizQuery.sendRandomQuizList();
        return ResponseEntity.ok().build();
    }
}
