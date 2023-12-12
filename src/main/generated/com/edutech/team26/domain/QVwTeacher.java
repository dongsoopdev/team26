package com.edutech.team26.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVwTeacher is a Querydsl query type for VwTeacher
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVwTeacher extends EntityPathBase<VwTeacher> {

    private static final long serialVersionUID = 718723317L;

    public static final QVwTeacher vwTeacher = new QVwTeacher("vwTeacher");

    public final DateTimePath<java.time.LocalDateTime> activeDate = createDateTime("activeDate", java.time.LocalDateTime.class);

    public final BooleanPath activeYn = createBoolean("activeYn");

    public final StringPath addr = createString("addr");

    public final StringPath addrDetail = createString("addrDetail");

    public final StringPath email = createString("email");

    public final StringPath fileOriginNm = createString("fileOriginNm");

    public final StringPath fileSaveNm = createString("fileSaveNm");

    public final StringPath intro = createString("intro");

    public final NumberPath<Long> mno = createNumber("mno", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modDate = createDateTime("modDate", java.time.LocalDateTime.class);

    public final StringPath phone = createString("phone");

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final StringPath status = createString("status");

    public final NumberPath<Long> teacherNo = createNumber("teacherNo", Long.class);

    public final StringPath userName = createString("userName");

    public final StringPath userStatus = createString("userStatus");

    public final StringPath zipcode = createString("zipcode");

    public QVwTeacher(String variable) {
        super(VwTeacher.class, forVariable(variable));
    }

    public QVwTeacher(Path<? extends VwTeacher> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVwTeacher(PathMetadata metadata) {
        super(VwTeacher.class, metadata);
    }

}

