package com.sendquiz.quiz.dto.request;

import com.sendquiz.global.eumtype.Subject;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizUpdate {

    private Long quizId;

    private String problem;

    private String answer;

    private Subject subject;

    @Builder
    public QuizUpdate(Long quizId, String problem, String answer, Subject subject) {
        this.quizId = quizId;
        this.problem = problem;
        this.answer = answer;
        this.subject = subject;
    }
}
