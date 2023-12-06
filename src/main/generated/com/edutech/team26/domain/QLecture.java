package com.edutech.team26.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLecture is a Querydsl query type for Lecture
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLecture extends EntityPathBase<Lecture> {

    private static final long serialVersionUID = -1840107118L;

    public static final QLecture lecture = new QLecture("lecture");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final ListPath<Category, QCategory> categorys = this.<Category, QCategory>createList("categorys", Category.class, QCategory.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> endEnrolmentDate = createDateTime("endEnrolmentDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> endStudyDate = createDateTime("endStudyDate", java.time.LocalDateTime.class);

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Long> lecture_id = createNumber("lecture_id", Long.class);

    public final NumberPath<Integer> lectureAct = createNumber("lectureAct", Integer.class);

    public final StringPath lectureContent = createString("lectureContent");

    public final NumberPath<Integer> lectureCurnum = createNumber("lectureCurnum", Integer.class);

    public final StringPath lectureImg1 = createString("lectureImg1");

    public final StringPath lectureImg2 = createString("lectureImg2");

    public final NumberPath<Integer> lectureMaxnum = createNumber("lectureMaxnum", Integer.class);

    public final NumberPath<Integer> lectureMinnum = createNumber("lectureMinnum", Integer.class);

    public final StringPath lectureName = createString("lectureName");

    public final StringPath lectureVedio = createString("lectureVedio");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final DateTimePath<java.time.LocalDateTime> startEnrolmentDate = createDateTime("startEnrolmentDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> startStudyDate = createDateTime("startStudyDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> teacher_no = createNumber("teacher_no", Long.class);

    public final StringPath zoomUrl = createString("zoomUrl");

    public QLecture(String variable) {
        super(Lecture.class, forVariable(variable));
    }

    public QLecture(Path<? extends Lecture> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLecture(PathMetadata metadata) {
        super(Lecture.class, metadata);
    }

}

