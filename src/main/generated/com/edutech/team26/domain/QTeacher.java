package com.edutech.team26.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeacher is a Querydsl query type for Teacher
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeacher extends EntityPathBase<Teacher> {

    private static final long serialVersionUID = 962588662L;

    public static final QTeacher teacher = new QTeacher("teacher");

    public final DateTimePath<java.time.LocalDateTime> activeDate = createDateTime("activeDate", java.time.LocalDateTime.class);

    public final BooleanPath activeYn = createBoolean("activeYn");

    public final StringPath fileOriginNm = createString("fileOriginNm");

    public final StringPath fileSaveNm = createString("fileSaveNm");

    public final StringPath intro = createString("intro");

    public final ListPath<Lecture, QLecture> lectures = this.<Lecture, QLecture>createList("lectures", Lecture.class, QLecture.class, PathInits.DIRECT2);

    public final NumberPath<Long> mno = createNumber("mno", Long.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final StringPath status = createString("status");

    public final NumberPath<Long> teacherNo = createNumber("teacherNo", Long.class);

    public QTeacher(String variable) {
        super(Teacher.class, forVariable(variable));
    }

    public QTeacher(Path<? extends Teacher> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeacher(PathMetadata metadata) {
        super(Teacher.class, metadata);
    }

}

