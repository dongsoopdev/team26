package com.edutech.team26.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTeacherHistory is a Querydsl query type for TeacherHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeacherHistory extends EntityPathBase<TeacherHistory> {

    private static final long serialVersionUID = -209794114L;

    public static final QTeacherHistory teacherHistory = new QTeacherHistory("teacherHistory");

    public final DateTimePath<java.time.LocalDateTime> activeDate = createDateTime("activeDate", java.time.LocalDateTime.class);

    public final BooleanPath activeYn = createBoolean("activeYn");

    public final NumberPath<Long> mno = createNumber("mno", Long.class);

    public final StringPath reason = createString("reason");

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> teacherHistoryNo = createNumber("teacherHistoryNo", Long.class);

    public QTeacherHistory(String variable) {
        super(TeacherHistory.class, forVariable(variable));
    }

    public QTeacherHistory(Path<? extends TeacherHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeacherHistory(PathMetadata metadata) {
        super(TeacherHistory.class, metadata);
    }

}

