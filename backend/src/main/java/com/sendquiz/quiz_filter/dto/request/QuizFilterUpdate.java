package com.sendquiz.quiz_filter.dto.request;

import com.sendquiz.member.domain.Member;
import com.sendquiz.quiz_filter.domain.QuizFilter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizFilterUpdate {

    private Boolean isNetwork;

    private Boolean isDatabase;

    private Boolean isOS;

    private Boolean isDataStructure;

    private Boolean isJava;

    private Boolean isSpring;

    private int numOfProblem;

    @Builder
    private QuizFilterUpdate(Boolean isNetwork, Boolean isDatabase, Boolean isOS, Boolean isDataStructure,
                             Boolean isJava, Boolean isSpring, int numOfProblem) {
        this.isNetwork = isNetwork;
        this.isDatabase = isDatabase;
        this.isOS = isOS;
        this.isDataStructure = isDataStructure;
        this.isJava = isJava;
        this.isSpring = isSpring;
        this.numOfProblem = numOfProblem;
    }
}
