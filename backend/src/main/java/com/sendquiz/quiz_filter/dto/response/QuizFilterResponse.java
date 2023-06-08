package com.sendquiz.quiz_filter.dto.response;

import com.sendquiz.quiz_filter.domain.QuizFilter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizFilterResponse {

    private boolean isNetwork;

    private boolean isDatabase;

    private boolean isOS;

    private boolean isDataStructure;

    private boolean isJava;

    private boolean isSpring;

    private int numOfProblem;

    @Builder
    private QuizFilterResponse(boolean isNetwork, boolean isDatabase, boolean isOS, boolean isDataStructure,
                              boolean isJava, boolean isSpring, int numOfProblem) {
        this.isNetwork = isNetwork;
        this.isDatabase = isDatabase;
        this.isOS = isOS;
        this.isDataStructure = isDataStructure;
        this.isJava = isJava;
        this.isSpring = isSpring;
        this.numOfProblem = numOfProblem;
    }

    public static QuizFilterResponse toQuizFilterResponse(QuizFilter quizFilter) {
        return QuizFilterResponse.builder()
                .isNetwork(quizFilter.isNetwork())
                .isDatabase(quizFilter.isDatabase())
                .isOS(quizFilter.isOS())
                .isDataStructure(quizFilter.isDataStructure())
                .isJava(quizFilter.isJava())
                .isSpring(quizFilter.isSpring())
                .numOfProblem(quizFilter.getNumOfProblem())
                .build();
    }
}
