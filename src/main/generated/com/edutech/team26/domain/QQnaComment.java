package com.edutech.team26.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQnaComment is a Querydsl query type for QnaComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQnaComment extends EntityPathBase<QnaComment> {

    private static final long serialVersionUID = -1938056377L;

    public static final QQnaComment qnaComment = new QQnaComment("qnaComment");

    public final NumberPath<Long> comment_no = createNumber("comment_no", Long.class);

    public final StringPath content = createString("content");

    public final NumberPath<Long> lecture_no = createNumber("lecture_no", Long.class);

    public final NumberPath<Long> mno = createNumber("mno", Long.class);

    public final NumberPath<Long> qna_no = createNumber("qna_no", Long.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public QQnaComment(String variable) {
        super(QnaComment.class, forVariable(variable));
    }

    public QQnaComment(Path<? extends QnaComment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQnaComment(PathMetadata metadata) {
        super(QnaComment.class, metadata);
    }

}

