package com.sendquiz.quiz.dto.request;

import com.sendquiz.global.eumtype.Subject;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizSearchDataDto {

    private int numOfProblem;

    private Subject subject;

    @Builder
    public QuizSearchDataDto(int numOfProblem, Subject subject) {
        this.numOfProblem = numOfProblem;
        this.subject = subject;
    }
}
