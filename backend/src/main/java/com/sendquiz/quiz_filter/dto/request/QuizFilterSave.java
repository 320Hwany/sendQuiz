package com.sendquiz.quiz_filter.dto.request;

import com.sendquiz.member.domain.Member;
import com.sendquiz.quiz_filter.domain.QuizFilter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.lang.Boolean.FALSE;

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
                .isNetwork(isNetwork != null ? isNetwork : FALSE)
                .isDatabase(isDatabase != null ? isDatabase : FALSE)
                .isOS(isOS != null ? isOS : false)
                .isDataStructure(isDataStructure != null ? isDataStructure : FALSE)
                .isJava(isJava != null ? isJava : FALSE)
                .isSpring(isSpring != null ? isSpring : FALSE)
                .numOfProblem(numOfProblem)
                .build();
    }


    public QuizFilterUpdate toQuizFilterUpdate() {
        return QuizFilterUpdate.builder()
                .isNetwork(isNetwork != null ? isNetwork : FALSE)
                .isDatabase(isDatabase != null ? isDatabase : FALSE)
                .isOS(isOS != null ? isOS : FALSE)
                .isDataStructure(isDataStructure != null ? isDataStructure : FALSE)
                .isJava(isJava != null ? isJava : FALSE)
                .isSpring(isSpring != null ? isSpring : FALSE)
                .numOfProblem(numOfProblem)
                .build();
    }
}
