package com.sendquiz.quiz.presentation.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizUpdate {

    private Long quizId;

    private String subject;

    private String problem;

    private String answer;

    @Builder
    public QuizUpdate(Long quizId, String subject, String problem, String answer) {
        this.quizId = quizId;
        this.subject = subject;
        this.problem = problem;
        this.answer = answer;
    }
}
