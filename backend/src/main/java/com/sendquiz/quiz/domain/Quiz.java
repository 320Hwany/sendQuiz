package com.sendquiz.quiz.domain;

import com.sendquiz.global.eumtype.Subject;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Quiz {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long id;

    private String problem;

    private String answer;

    private Subject subject;

    @Builder
    public Quiz(String problem, String answer, Subject subject) {
        this.problem = problem;
        this.answer = answer;
        this.subject = subject;
    }
}
