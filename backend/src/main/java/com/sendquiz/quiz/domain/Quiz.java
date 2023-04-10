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

    @Enumerated(EnumType.STRING)
    private Subject subject;

    @Builder
    public Quiz(Subject subject, String problem, String answer) {
        this.subject = subject;
        this.problem = problem;
        this.answer = answer;
    }
}
