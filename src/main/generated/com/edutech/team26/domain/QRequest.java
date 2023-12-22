package com.edutech.team26.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRequest is a Querydsl query type for Request
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRequest extends EntityPathBase<Request> {

    private static final long serialVersionUID = -797108573L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRequest request = new QRequest("request");

    public final StringPath content = createString("content");

    public final QLecture lecture;

    public final NumberPath<Long> mno = createNumber("mno", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modDate = createDateTime("modDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> request_no = createNumber("request_no", Long.class);

    public final StringPath title = createString("title");

    public QRequest(String variable) {
        this(Request.class, forVariable(variable), INITS);
    }

    public QRequest(Path<? extends Request> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRequest(PathMetadata metadata, PathInits inits) {
        this(Request.class, metadata, inits);
    }

    public QRequest(Class<? extends Request> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lecture = inits.isInitialized("lecture") ? new QLecture(forProperty("lecture"), inits.get("lecture")) : null;
    }

}

