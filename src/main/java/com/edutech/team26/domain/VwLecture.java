package com.edutech.team26.domain;

import groovy.transform.Immutable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

// 생성한 뷰 가져오기
@Immutable
@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lecturelist")
public class VwLecture {
    @Id
    @Column(name="lecture_no")
    private long lectureNo;
    private String lectureName;
    private String lectureContent;
    private LocalDateTime startEnrolmentDate;                //수강신청일
    private LocalDateTime endEnrolmentDate;                  //수강종료일
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

    private Long mno;
    private LocalDateTime modDate;
    private LocalDateTime regDate;
    private String userName;                        //선생님 이름
    private String userStatus;

    @Column(name="teacher_no")
    private Long teacherNo;
    private LocalDateTime activeDate;
    private Boolean activeYn;
    private String intro;
    private String status;


}
