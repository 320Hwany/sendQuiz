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

    public final com.sendquiz.global.QBaseTimeEntity _super = new com.sendquiz.global.QBaseTimeEntity(this);

    public final StringPath certificationNum = createString("certificationNum");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDateTime = _super.createDateTime;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDateTime = _super.lastModifiedDateTime;

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

