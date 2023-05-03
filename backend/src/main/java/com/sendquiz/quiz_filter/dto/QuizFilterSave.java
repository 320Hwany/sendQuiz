package com.sendquiz.quiz_filter.dto;

import com.sendquiz.member.domain.Member;
import com.sendquiz.quiz_filter.domain.QuizFilter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizFilterSave {

    private boolean network;

    private boolean database;

    private boolean operatingSystem;

    private boolean dataStructure;

    private boolean java;

    private boolean spring;

    private int numOfProblem;

    @Builder
    private QuizFilterSave(boolean network, boolean database, boolean operatingSystem,
                          boolean dataStructure, boolean java, boolean spring, int numOfProblem) {
        this.network = network;
        this.database = database;
        this.operatingSystem = operatingSystem;
        this.dataStructure = dataStructure;
        this.java = java;
        this.spring = spring;
        this.numOfProblem = numOfProblem;
    }

    public QuizFilter toEntity(Member member) {
        return QuizFilter.builder()
                .member(member)
                .isNetwork(network)
                .isDatabase(database)
                .isOS(operatingSystem)
                .isDataStructure(dataStructure)
                .isJava(java)
                .isSpring(spring)
                .numOfProblem(numOfProblem)
                .build();
    }
}
