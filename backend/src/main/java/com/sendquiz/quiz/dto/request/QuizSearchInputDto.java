package com.sendquiz.quiz.dto.request;

import com.sendquiz.global.eumtype.Subject;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizSearchInputDto {

    private int numOfProblem;

    private String subject;

    @Builder
    private QuizSearchInputDto(int numOfProblem, String subject) {
        this.numOfProblem = numOfProblem;
        this.subject = subject;
    }

    public QuizSearchDataDto toDataDto() {
        return QuizSearchDataDto.builder()
                .numOfProblem(numOfProblem)
                .subject(Subject.valueOf(subject))
                .build();
    }
}
