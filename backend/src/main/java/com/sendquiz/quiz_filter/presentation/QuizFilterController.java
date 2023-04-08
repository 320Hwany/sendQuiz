package com.sendquiz.quiz_filter.presentation;

import com.sendquiz.global.annotation.Login;
import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.quiz_filter.application.QuizFilterService;
import com.sendquiz.quiz_filter.dto.QuizFilterSave;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class QuizFilterController {

    private final QuizFilterService quizFilterService;

    @PostMapping("/quizFilter")
    public ResponseEntity<Void> save(@Login MemberSession memberSession,
                                     @RequestBody QuizFilterSave quizFilterSave) {
        quizFilterService.save(quizFilterSave, memberSession);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
