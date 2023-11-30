package com.edutech.team26.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 385192966L;

    public static final QMember member = new QMember("member1");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath addr = createString("addr");

    public final StringPath addrDetail = createString("addrDetail");

    public final StringPath email = createString("email");

    public final StringPath emailAuthKey = createString("emailAuthKey");

    public final DateTimePath<java.time.LocalDateTime> emailAuthTime = createDateTime("emailAuthTime", java.time.LocalDateTime.class);

    public final BooleanPath emailAuthYn = createBoolean("emailAuthYn");

    public final DateTimePath<java.time.LocalDateTime> lastLoginAt = createDateTime("lastLoginAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> mno = createNumber("mno", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final StringPath resetPasswordKey = createString("resetPasswordKey");

    public final DateTimePath<java.time.LocalDateTime> resetPasswordLimitTime = createDateTime("resetPasswordLimitTime", java.time.LocalDateTime.class);

    public final SetPath<com.edutech.team26.constant.MemberRole, EnumPath<com.edutech.team26.constant.MemberRole>> roleSet = this.<com.edutech.team26.constant.MemberRole, EnumPath<com.edutech.team26.constant.MemberRole>>createSet("roleSet", com.edutech.team26.constant.MemberRole.class, EnumPath.class, PathInits.DIRECT2);

    public final StringPath userName = createString("userName");

    public final StringPath userStatus = createString("userStatus");

    public final StringPath zipcode = createString("zipcode");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

