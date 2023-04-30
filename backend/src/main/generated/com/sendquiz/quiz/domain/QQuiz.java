package com.sendquiz.quiz.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuiz is a Querydsl query type for Quiz
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuiz extends EntityPathBase<Quiz> {

    private static final long serialVersionUID = -1496136438L;

    public static final QQuiz quiz = new QQuiz("quiz");

    public final com.sendquiz.global.QBaseTimeEntity _super = new com.sendquiz.global.QBaseTimeEntity(this);

    public final StringPath answer = createString("answer");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDateTime = _super.createDateTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDateTime = _super.lastModifiedDateTime;

    public final StringPath problem = createString("problem");

    public final EnumPath<com.sendquiz.global.eumtype.Subject> subject = createEnum("subject", com.sendquiz.global.eumtype.Subject.class);

    public QQuiz(String variable) {
        super(Quiz.class, forVariable(variable));
    }

    public QQuiz(Path<? extends Quiz> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuiz(PathMetadata metadata) {
        super(Quiz.class, metadata);
    }

}

