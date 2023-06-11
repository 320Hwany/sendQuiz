package com.sendquiz.quiz_filter.presentation;

import com.sendquiz.global.annotation.Login;
import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.quiz_filter.application.QuizFilterCommand;
import com.sendquiz.quiz_filter.application.QuizFilterQuery;
import com.sendquiz.quiz_filter.dto.response.QuizFilterResponse;
import com.sendquiz.quiz_filter.dto.request.QuizFilterSave;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class QuizFilterController {

    private final QuizFilterCommand quizFilterCommand;
    private final QuizFilterQuery quizFilterQuery;

    @PostMapping("/quizFilter")
    public ResponseEntity<Void> saveQuizFilter(@Login MemberSession memberSession,
                                               @RequestBody QuizFilterSave quizFilterSave) {
        quizFilterCommand.save(quizFilterSave, memberSession);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/quizFilter")
    public ResponseEntity<QuizFilterResponse> getQuizFilter(@Login MemberSession memberSession) {
        return ResponseEntity.ok(quizFilterQuery.get(memberSession.getId()));
    }
}
