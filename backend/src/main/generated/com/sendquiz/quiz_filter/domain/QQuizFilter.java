package com.sendquiz.quiz_filter.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuizFilter is a Querydsl query type for QuizFilter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuizFilter extends EntityPathBase<QuizFilter> {

    private static final long serialVersionUID = -764206041L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuizFilter quizFilter = new QQuizFilter("quizFilter");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDatabase = createBoolean("isDatabase");

    public final BooleanPath isDataStructure = createBoolean("isDataStructure");

    public final BooleanPath isJava = createBoolean("isJava");

    public final BooleanPath isNetwork = createBoolean("isNetwork");

    public final BooleanPath isOS = createBoolean("isOS");

    public final BooleanPath isSpring = createBoolean("isSpring");

    public final com.sendquiz.member.domain.QMember member;

    public final NumberPath<Integer> numOfProblem = createNumber("numOfProblem", Integer.class);

    public QQuizFilter(String variable) {
        this(QuizFilter.class, forVariable(variable), INITS);
    }

    public QQuizFilter(Path<? extends QuizFilter> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuizFilter(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuizFilter(PathMetadata metadata, PathInits inits) {
        this(QuizFilter.class, metadata, inits);
    }

    public QQuizFilter(Class<? extends QuizFilter> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.sendquiz.member.domain.QMember(forProperty("member")) : null;
    }

}

