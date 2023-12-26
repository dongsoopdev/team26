package com.edutech.team26.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Getter
@Entity
@ToString
@Table(name = "courselist")
public class VwCourse {

    @Id
    private Long studentNo;
    private Long studentMno;
    private int entranceYn;
    private String studentStatus;  // 상태(REQ-수강신청완료, COMPLETE- 수강 중, CANCEL- 수강 취소)
    private LocalDateTime studentRegDate;

    @Column(name="lecture_no")
    private long lectureNo;
    private String lectureName;
    private String lectureContent;
    private LocalDateTime startEnrolmentDate;                  //수강신청일
    private LocalDateTime endEnrolmentDate;                    //수강종료일
    private LocalDateTime startStudyDate;                      //강의시작일
    private LocalDateTime endStudyDate;                        //강의종료일
    private String lectureImg1;
    private String lectureImg2;
    private String lectureVedio;
    private int lectureCurnum;
    private int lectureMinnum;
    private int lectureMaxnum;
    private int lectureAct;
    private String zoomUrl;
    private LocalDateTime zoomDate;

    @Column(name="teacher_no")
    private Long teacherNo;
    private Long teacherMno;
    private LocalDateTime activeDate;
    private Boolean activeYn;
    private String intro;
    private String  teacherStatus;
    private String teacherName;               //강사이름

    private Long mno;
    private String userName;                 //수강생이름
    private String userStatus;

}