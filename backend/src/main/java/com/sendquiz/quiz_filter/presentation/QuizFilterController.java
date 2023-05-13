package com.sendquiz.quiz_filter.presentation;

import com.sendquiz.global.annotation.Login;
import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.quiz_filter.application.QuizFilterService;
import com.sendquiz.quiz_filter.application.response.QuizFilterResponse;
import com.sendquiz.quiz_filter.presentation.request.QuizFilterSave;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class QuizFilterController {

    private final QuizFilterService quizFilterService;

    @PostMapping("/quizFilter")
    public ResponseEntity<Void> save(@Login MemberSession memberSession,
                                     @RequestBody QuizFilterSave quizFilterSave) {
        quizFilterService.save(quizFilterSave, memberSession);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/quizFilter")
    public ResponseEntity<QuizFilterResponse> get(@Login MemberSession memberSession) {
        return ResponseEntity.ok(quizFilterService.get(memberSession.getId()));
    }
}
