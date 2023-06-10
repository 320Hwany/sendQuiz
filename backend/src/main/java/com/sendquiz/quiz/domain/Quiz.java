package com.sendquiz.quiz.domain;

import com.sendquiz.global.BaseTimeEntity;
import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.quiz.dto.request.QuizSearch;
import com.sendquiz.quiz.dto.request.QuizUpdate;
import com.sendquiz.quiz_filter.dto.request.QuizFilterSearch;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Quiz extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long id;

    private String problem;

    @Lob
    private String answer;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    @Builder
    private Quiz(Subject subject, String problem, String answer) {
        this.subject = subject;
        this.problem = problem;
        this.answer = answer;
    }

    public void update(QuizUpdate quizUpdate, Subject subject) {
        this.problem = quizUpdate.getProblem();
        this.answer = quizUpdate.getAnswer();
        this.subject = subject;
    }

    public boolean filterQuiz(QuizFilterSearch quizFilterSearch) {
        return quizFilterSearch.filterQuiz(this);
    }

    public boolean filterQuiz(QuizSearch quizSearch) {
        return quizSearch.filterQuiz(this);
    }
}
