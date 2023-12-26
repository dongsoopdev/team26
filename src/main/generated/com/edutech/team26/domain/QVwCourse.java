package com.edutech.team26.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVwCourse is a Querydsl query type for VwCourse
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVwCourse extends EntityPathBase<VwCourse> {

    private static final long serialVersionUID = 1208902824L;

    public static final QVwCourse vwCourse = new QVwCourse("vwCourse");

    public final DateTimePath<java.time.LocalDateTime> activeDate = createDateTime("activeDate", java.time.LocalDateTime.class);

    public final BooleanPath activeYn = createBoolean("activeYn");

    public final DateTimePath<java.time.LocalDateTime> endEnrolmentDate = createDateTime("endEnrolmentDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> endStudyDate = createDateTime("endStudyDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> entranceYn = createNumber("entranceYn", Integer.class);

    public final StringPath intro = createString("intro");

    public final NumberPath<Integer> lectureAct = createNumber("lectureAct", Integer.class);

    public final StringPath lectureContent = createString("lectureContent");

    public final NumberPath<Integer> lectureCurnum = createNumber("lectureCurnum", Integer.class);

    public final StringPath lectureImg1 = createString("lectureImg1");

    public final StringPath lectureImg2 = createString("lectureImg2");

    public final NumberPath<Integer> lectureMaxnum = createNumber("lectureMaxnum", Integer.class);

    public final NumberPath<Integer> lectureMinnum = createNumber("lectureMinnum", Integer.class);

    public final StringPath lectureName = createString("lectureName");

    public final NumberPath<Long> lectureNo = createNumber("lectureNo", Long.class);

    public final StringPath lectureVedio = createString("lectureVedio");

    public final NumberPath<Long> mno = createNumber("mno", Long.class);

    public final DateTimePath<java.time.LocalDateTime> startEnrolmentDate = createDateTime("startEnrolmentDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> startStudyDate = createDateTime("startStudyDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> studentMno = createNumber("studentMno", Long.class);

    public final NumberPath<Long> studentNo = createNumber("studentNo", Long.class);

    public final DateTimePath<java.time.LocalDateTime> studentRegDate = createDateTime("studentRegDate", java.time.LocalDateTime.class);

    public final StringPath studentStatus = createString("studentStatus");

    public final NumberPath<Long> teacherMno = createNumber("teacherMno", Long.class);

    public final StringPath teacherName = createString("teacherName");

    public final NumberPath<Long> teacherNo = createNumber("teacherNo", Long.class);

    public final StringPath teacherStatus = createString("teacherStatus");

    public final StringPath userName = createString("userName");

    public final StringPath userStatus = createString("userStatus");

    public final DateTimePath<java.time.LocalDateTime> zoomDate = createDateTime("zoomDate", java.time.LocalDateTime.class);

    public final StringPath zoomUrl = createString("zoomUrl");

    public QVwCourse(String variable) {
        super(VwCourse.class, forVariable(variable));
    }

    public QVwCourse(Path<? extends VwCourse> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVwCourse(PathMetadata metadata) {
        super(VwCourse.class, metadata);
    }

}

