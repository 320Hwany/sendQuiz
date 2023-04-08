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

    private boolean isNetwork;

    private boolean isDatabase;

    private boolean isOS;

    private boolean isDataStructure;

    private boolean isJava;

    private boolean isSpring;

    private int numOfProblem;

    @Builder
    public QuizFilterSave(boolean isNetwork, boolean isDatabase, boolean isOS, boolean isDataStructure,
                          boolean isJava, boolean isSpring, int numOfProblem) {
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
                .isNetwork(isNetwork)
                .isDatabase(isDatabase)
                .isOS(isOS)
                .isDataStructure(isDataStructure)
                .isJava(isJava)
                .isSpring(isSpring)
                .numOfProblem(numOfProblem)
                .build();
    }
}
