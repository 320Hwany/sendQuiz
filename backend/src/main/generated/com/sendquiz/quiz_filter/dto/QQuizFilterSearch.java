package com.sendquiz.quiz_filter.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sendquiz.quiz_filter.dto.QQuizFilterSearch is a Querydsl Projection type for QuizFilterSearch
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QQuizFilterSearch extends ConstructorExpression<QuizFilterSearch> {

    private static final long serialVersionUID = -1788981740L;

    public QQuizFilterSearch(com.querydsl.core.types.Expression<Boolean> isNetwork, com.querydsl.core.types.Expression<Boolean> isDatabase, com.querydsl.core.types.Expression<Boolean> isOS, com.querydsl.core.types.Expression<Boolean> isDataStructure, com.querydsl.core.types.Expression<Boolean> isJava, com.querydsl.core.types.Expression<Boolean> isSpring, com.querydsl.core.types.Expression<Integer> numOfProblem) {
        super(QuizFilterSearch.class, new Class<?>[]{boolean.class, boolean.class, boolean.class, boolean.class, boolean.class, boolean.class, int.class}, isNetwork, isDatabase, isOS, isDataStructure, isJava, isSpring, numOfProblem);
    }

}

