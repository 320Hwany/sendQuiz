package com.sendquiz.quiz.domain;

import com.sendquiz.global.BaseTimeEntity;
import com.sendquiz.global.eumtype.Subject;
import com.sendquiz.quiz_filter.application.request.QuizFilterSearch;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.sendquiz.global.eumtype.Subject.*;

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

    public boolean filterQuizList(QuizFilterSearch quizFilterSearch) {
        if (quizFilterSearch.isNetwork() && getSubject() == NETWORK) {
            return true;
        }
        if (quizFilterSearch.isDatabase() && getSubject() == DATA_BASE) {
            return true;
        }
        if (quizFilterSearch.isOS() && getSubject() == OPERATING_SYSTEM) {
            return true;
        }
        if (quizFilterSearch.isDataStructure() && getSubject() == DATA_STRUCTURE) {
            return true;
        }
        if (quizFilterSearch.isJava() && getSubject() == JAVA) {
            return true;
        }
        if (quizFilterSearch.isSpring() && getSubject() == SPRING) {
            return true;
        }
        return false;
    }
}
