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

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLecture lecture = new QLecture("lecture");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final ListPath<Category, QCategory> categorys = this.<Category, QCategory>createList("categorys", Category.class, QCategory.class, PathInits.DIRECT2);

    public final StringPath endEnrolmentDate = createString("endEnrolmentDate");

    public final StringPath endStudyDate = createString("endStudyDate");

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Long> lecture_no = createNumber("lecture_no", Long.class);

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

    public final StringPath startEnrolmentDate = createString("startEnrolmentDate");

    public final StringPath startStudyDate = createString("startStudyDate");

    public final QTeacher teacher;

    public final StringPath zoomUrl = createString("zoomUrl");

    public QLecture(String variable) {
        this(Lecture.class, forVariable(variable), INITS);
    }

    public QLecture(Path<? extends Lecture> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLecture(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLecture(PathMetadata metadata, PathInits inits) {
        this(Lecture.class, metadata, inits);
    }

    public QLecture(Class<? extends Lecture> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.teacher = inits.isInitialized("teacher") ? new QTeacher(forProperty("teacher")) : null;
    }

}

