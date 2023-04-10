package com.sendquiz.quiz.presentation;

import com.sendquiz.quiz.application.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/quizList")
    public ResponseEntity<Void> getQuizList() {
        quizService.sendQuizList();
        return ResponseEntity.ok().build();
    }
}
