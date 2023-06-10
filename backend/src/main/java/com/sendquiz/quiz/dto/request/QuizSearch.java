package com.sendquiz.quiz.dto.request;

import com.sendquiz.quiz.domain.Quiz;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.sendquiz.global.eumtype.Subject.*;
import static com.sendquiz.global.eumtype.Subject.SPRING;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizSearch {

    private boolean network;

    private boolean database;

    private boolean operatingSystem;

    private boolean dataStructure;

    private boolean java;

    private boolean spring;

    private int page;

    @Builder
    private QuizSearch(boolean network, boolean database, boolean operatingSystem,
                      boolean dataStructure, boolean java, boolean spring, int page) {
        this.network = network;
        this.database = database;
        this.operatingSystem = operatingSystem;
        this.dataStructure = dataStructure;
        this.java = java;
        this.spring = spring;
        this.page = page;
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
        return isOperatingSystem() && quiz.getSubject() == OPERATING_SYSTEM;
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
