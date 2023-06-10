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

    private boolean isNetwork;

    private boolean isDatabase;

    private boolean isOS;

    private boolean isDataStructure;

    private boolean isJava;

    private boolean isSpring;

    private int numOfProblem;

    private String email;

    @Builder
    @QueryProjection
    public QuizFilterSearch(boolean isNetwork, boolean isDatabase, boolean isOS, boolean isDataStructure,
                            boolean isJava, boolean isSpring, int numOfProblem, String email) {
        this.isNetwork = isNetwork;
        this.isDatabase = isDatabase;
        this.isOS = isOS;
        this.isDataStructure = isDataStructure;
        this.isJava = isJava;
        this.isSpring = isSpring;
        this.numOfProblem = numOfProblem;
        this.email = email;
    }

    public boolean filterQuiz(Quiz quiz) {
        return containNetwork(quiz) || containDatabase(quiz) ||
                containOS(quiz) || containDataStructure(quiz) ||
                containJava(quiz) || containSpring(quiz);
    }

    private boolean containNetwork(Quiz quiz) {
        return isNetwork() && quiz.getSubject() == NETWORK;
    }

    private boolean containDatabase(Quiz quiz) {
        return isDatabase() && quiz.getSubject() == DATA_BASE;
    }

    private boolean containOS(Quiz quiz) {
        return isOS() && quiz.getSubject() == OPERATING_SYSTEM;
    }

    private boolean containDataStructure(Quiz quiz) {
        return isDataStructure() && quiz.getSubject() == DATA_STRUCTURE;
    }

    private boolean containJava(Quiz quiz) {
        return isJava() && quiz.getSubject() == JAVA;
    }

    private boolean containSpring(Quiz quiz) {
        return isSpring() && quiz.getSubject() == SPRING;
    }
}
