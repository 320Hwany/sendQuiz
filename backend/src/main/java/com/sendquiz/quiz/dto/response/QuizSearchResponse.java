package com.sendquiz.quiz.dto.response;

import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.quiz.domain.Quiz;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizSearchResponse {

    private String problem;

    private String answer;

    private Subject subject;

    @Builder
    private QuizSearchResponse(String problem, String answer, Subject subject) {
        this.problem = problem;
        this.answer = answer;
        this.subject = subject;
    }

    public static QuizSearchResponse toQuizSearchResponse(Quiz quiz) {
        return QuizSearchResponse.builder()
                .problem(quiz.getProblem())
                .answer(quiz.getAnswer())
                .subject(quiz.getSubject())
                .build();
    }
}
