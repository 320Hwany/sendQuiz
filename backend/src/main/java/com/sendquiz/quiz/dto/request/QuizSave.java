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

    private String subject;

    private String problem;

    private String answer;

    @Builder
    public QuizSave(String subject, String problem, String answer) {
        this.subject = subject;
        this.problem = problem;
        this.answer = answer;
    }

    public Quiz toEntity(Subject subject) {
        return Quiz.builder()
                .subject(subject)
                .problem(problem)
                .answer(answer)
                .build();
    }
}
