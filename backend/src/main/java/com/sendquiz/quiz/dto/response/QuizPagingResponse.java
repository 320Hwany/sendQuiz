package com.sendquiz.quiz.dto.response;

import com.sendquiz.global.eumtype.Subject;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizPagingResponse {

    private int number;

    private String problem;

    private String answer;

    private Subject subject;

    @Builder
    private QuizPagingResponse(int number, String problem, String answer, Subject subject) {
        this.number = number;
        this.problem = problem;
        this.answer = answer;
        this.subject = subject;
    }

    public static QuizPagingResponse toQuizPagingResponse(QuizSearchResponse quizSearchResponse, int number) {
        return QuizPagingResponse.builder()
                .number(number + 1)
                .problem(quizSearchResponse.getProblem())
                .answer(quizSearchResponse.getAnswer())
                .subject(quizSearchResponse.getSubject())
                .build();
    }
}
