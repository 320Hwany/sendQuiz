package com.sendquiz.quiz.presentation;

import com.sendquiz.global.annotation.AdminLogin;
import com.sendquiz.global.annotation.Login;
import com.sendquiz.member.domain.AdminSession;
import com.sendquiz.member.domain.MemberSession;
import com.sendquiz.quiz.application.QuizCommand;
import com.sendquiz.quiz.application.QuizQuery;
import com.sendquiz.quiz.dto.request.QuizSave;
import com.sendquiz.quiz.dto.request.QuizSearch;
import com.sendquiz.quiz.dto.request.QuizUpdate;
import com.sendquiz.quiz.dto.response.QuizPagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class QuizController {

    private final QuizCommand quizCommand;
    private final QuizQuery quizQuery;

    @PostMapping("/quiz")
    public ResponseEntity<Void> saveQuiz(@AdminLogin AdminSession adminSession,
                                         @RequestBody QuizSave quizSave) {
        quizCommand.save(quizSave);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/quiz")
    public ResponseEntity<Void> updateQuiz(@AdminLogin AdminSession adminSession,
                                           @RequestBody QuizUpdate quizUpdate) {
        quizCommand.update(quizUpdate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/quiz")
    public ResponseEntity<List<QuizPagingResponse>> getQuizzes(@Login MemberSession memberSession,
                                                               @ModelAttribute QuizSearch quizSearch) {
        List<QuizPagingResponse> pagingQuizzesResponse = quizQuery.findAllWithFilter(quizSearch);
        return ResponseEntity.ok(pagingQuizzesResponse);
    }
}
