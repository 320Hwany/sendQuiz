package com.sendquiz.quiz_filter.dto.request;

import com.querydsl.core.annotations.QueryProjection;
import com.sendquiz.quiz.domain.Quiz;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.sendquiz.global.eumtype.Subject.*;
import static com.sendquiz.global.eumtype.Subject.SPRING;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizFilterSearch {

    private Boolean isNetwork;

    private Boolean isDatabase;

    private Boolean isOS;

    private Boolean isDataStructure;

    private Boolean isJava;

    private Boolean isSpring;

    private int numOfProblem;

    private String email;

    @Builder
    @QueryProjection
    public QuizFilterSearch(Boolean isNetwork, Boolean isDatabase, Boolean isOS, Boolean isDataStructure,
                            Boolean isJava, Boolean isSpring, int numOfProblem, String email) {
        this.isNetwork = isNetwork;
        this.isDatabase = isDatabase;
        this.isOS = isOS;
        this.isDataStructure = isDataStructure;
        this.isJava = isJava;
        this.isSpring = isSpring;
        this.numOfProblem = numOfProblem;
        this.email = email;
    }

    public Boolean filterQuiz(Quiz quiz) {
        return containNetwork(quiz) || containDatabase(quiz) ||
                containOS(quiz) || containDataStructure(quiz) ||
                containJava(quiz) || containSpring(quiz);
    }

    private Boolean containNetwork(Quiz quiz) {
        return isNetwork && quiz.getSubject() == NETWORK;
    }

    private Boolean containDatabase(Quiz quiz) {
        return isDatabase && quiz.getSubject() == DATA_BASE;
    }

    private Boolean containOS(Quiz quiz) {
        return isOS && quiz.getSubject() == OPERATING_SYSTEM;
    }

    private Boolean containDataStructure(Quiz quiz) {
        return isDataStructure && quiz.getSubject() == DATA_STRUCTURE;
    }

    private Boolean containJava(Quiz quiz) {
        return isJava && quiz.getSubject() == JAVA;
    }

    private Boolean containSpring(Quiz quiz) {
        return isSpring && quiz.getSubject() == SPRING;
    }
}
