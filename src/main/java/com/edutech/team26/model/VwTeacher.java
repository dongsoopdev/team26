package com.edutech.team26.model;

import groovy.transform.Immutable;
import jakarta.persistence.*;
import org.hibernate.annotations.Subselect;

import java.time.LocalDateTime;

// 생성한 뷰 가져오기
@Immutable
@Entity
@Table(name = "teacherlist")
public class VwTeacher {
    @Id
    private Long mno;

    private LocalDateTime modDate;

    private LocalDateTime regDate;

    private String addr;

    private String addr_detail;

    private String email;

    private String phone;

    private String userName;

    private String zipcode;

    private String userStatus;

    private Long teacherNo;

    private LocalDateTime activeDate;

    private Boolean activeYn;

    private String fileOriginNm;

    private String fileSaveNm;

    private String intro;

    private String status;
}
