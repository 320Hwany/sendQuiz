package com.sendquiz.certification.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCertification is a Querydsl query type for Certification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCertification extends EntityPathBase<Certification> {

    private static final long serialVersionUID = 689558120L;

    public static final QCertification certification = new QCertification("certification");

    public final StringPath certificationNum = createString("certificationNum");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QCertification(String variable) {
        super(Certification.class, forVariable(variable));
    }

    public QCertification(Path<? extends Certification> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCertification(PathMetadata metadata) {
        super(Certification.class, metadata);
    }

}

