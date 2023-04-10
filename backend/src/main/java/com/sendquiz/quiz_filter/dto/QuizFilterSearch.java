package com.sendquiz.quiz_filter.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
