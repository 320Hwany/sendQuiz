package com.sendquiz.quiz_filter.dto.request;

import com.sendquiz.member.domain.Member;
import com.sendquiz.quiz_filter.domain.QuizFilter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizFilterSave {

    private Boolean isNetwork;

    private Boolean isDatabase;

    private Boolean isOS;

    private Boolean isDataStructure;

    private Boolean isJava;

    private Boolean isSpring;

    private int numOfProblem;

    @Builder
    private QuizFilterSave(Boolean isNetwork, Boolean isDatabase, Boolean isOS,
                           Boolean isDataStructure, Boolean isJava, Boolean isSpring, int numOfProblem) {
        this.isNetwork = isNetwork;
        this.isDatabase = isDatabase;
        this.isOS = isOS;
        this.isDataStructure = isDataStructure;
        this.isJava = isJava;
        this.isSpring = isSpring;
        this.numOfProblem = numOfProblem;
    }

    public QuizFilter toEntity(Member member) {
        return QuizFilter.builder()
                .member(member)
                .isNetwork(isNetwork != null ? isNetwork : false)
                .isDatabase(isDatabase != null ? isDatabase : false)
                .isOS(isOS != null ? isOS : false)
                .isDataStructure(isDataStructure != null ? isDataStructure : false)
                .isJava(isJava != null ? isJava : false)
                .isSpring(isSpring != null ? isSpring : false)
                .numOfProblem(numOfProblem)
                .build();
    }


    public QuizFilterUpdate toQuizFilterUpdate() {
        return QuizFilterUpdate.builder()
                .isNetwork(isNetwork != null ? isNetwork : false)
                .isDatabase(isDatabase != null ? isDatabase : false)
                .isOS(isOS != null ? isOS : false)
                .isDataStructure(isDataStructure != null ? isDataStructure : false)
                .isJava(isJava != null ? isJava : false)
                .isSpring(isSpring != null ? isSpring : false)
                .numOfProblem(numOfProblem)
                .build();
    }
}
