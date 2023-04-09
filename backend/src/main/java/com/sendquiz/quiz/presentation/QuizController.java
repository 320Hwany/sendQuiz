package com.sendquiz.quiz.presentation;

import com.sendquiz.quiz.application.QuizService;
import com.sendquiz.quiz.dto.request.QuizSearchDataDto;
import com.sendquiz.quiz.dto.request.QuizSearchInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/quizList")
    public void getQuizList(@ModelAttribute QuizSearchInputDto quizSearchInputDto) {
        quizService.getQuizList(quizSearchInputDto);
    }
}
