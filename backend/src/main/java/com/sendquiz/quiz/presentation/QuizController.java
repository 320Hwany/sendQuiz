package com.sendquiz.quiz.presentation;

import com.sendquiz.global.annotation.AdminLogin;
import com.sendquiz.member.domain.AdminSession;
import com.sendquiz.quiz.application.QuizCommand;
import com.sendquiz.quiz.application.QuizQuery;
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

    private final QuizQuery quizQuery;
    private final QuizCommand quizCommand;

    @GetMapping("/quizList")
    public ResponseEntity<Void> getQuizList() {
        quizQuery.sendRandomQuizList();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/quiz")
    public ResponseEntity<Void> save(@AdminLogin AdminSession adminSession,
                                     @RequestBody QuizSave quizSave) {
        quizCommand.save(quizSave);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/quiz")
    public ResponseEntity<Void> update(@AdminLogin AdminSession adminSession,
                                       @RequestBody QuizUpdate quizUpdate) {
        quizCommand.update(quizUpdate);
        return ResponseEntity.ok().build();
    }
}
