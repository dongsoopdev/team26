package com.edutech.team26.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVwLecture is a Querydsl query type for VwLecture
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVwLecture extends EntityPathBase<VwLecture> {

    private static final long serialVersionUID = -2083972463L;

    public static final QVwLecture vwLecture = new QVwLecture("vwLecture");

    public final DateTimePath<java.time.LocalDateTime> activeDate = createDateTime("activeDate", java.time.LocalDateTime.class);

    public final BooleanPath activeYn = createBoolean("activeYn");

    public final DateTimePath<java.time.LocalDateTime> endEnrolmentDate = createDateTime("endEnrolmentDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> endStudyDate = createDateTime("endStudyDate", java.time.LocalDateTime.class);

    public final StringPath intro = createString("intro");

    public final DateTimePath<java.time.LocalDateTime> lecModDate = createDateTime("lecModDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lecRegDate = createDateTime("lecRegDate", java.time.LocalDateTime.class);

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

    public final DateTimePath<java.time.LocalDateTime> modDate = createDateTime("modDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> startEnrolmentDate = createDateTime("startEnrolmentDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> startStudyDate = createDateTime("startStudyDate", java.time.LocalDateTime.class);

    public final StringPath status = createString("status");

    public final NumberPath<Long> teacherNo = createNumber("teacherNo", Long.class);

    public final StringPath userName = createString("userName");

    public final StringPath userStatus = createString("userStatus");

    public final DateTimePath<java.time.LocalDateTime> zoomDate = createDateTime("zoomDate", java.time.LocalDateTime.class);

    public final StringPath zoomUrl = createString("zoomUrl");

    public QVwLecture(String variable) {
        super(VwLecture.class, forVariable(variable));
    }

    public QVwLecture(Path<? extends VwLecture> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVwLecture(PathMetadata metadata) {
        super(VwLecture.class, metadata);
    }

}

