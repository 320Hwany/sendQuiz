package com.sendquiz.quiz.dto.request;

import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.quiz.domain.Quiz;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizSave {

    private String problem;

    private String answer;

    private String subject;

    @Builder
    public QuizSave(String problem, String answer, String subject) {
        this.problem = problem;
        this.answer = answer;
        this.subject = subject;
    }

    public Quiz toEntity() {
        return Quiz.builder()
                .problem(problem)
                .answer(answer)
                .subject(Subject.valueOf(subject))
                .build();
    }
}
