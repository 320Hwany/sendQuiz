package com.sendquiz.quiz.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizzesResult {

    private int totalPage;

    private List<QuizPagingResponse> quizzesPagingResponse;

    @Builder
    public QuizzesResult(int totalPage, List<QuizPagingResponse> quizzesPagingResponse) {
        this.totalPage = totalPage;
        this.quizzesPagingResponse = quizzesPagingResponse;
    }
}
