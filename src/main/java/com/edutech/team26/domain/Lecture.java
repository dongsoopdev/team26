package com.edutech.team26.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class Lecture extends BaseEntity{
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lecture_no;

    long teacher_no;


    //다대다
    @ManyToMany(mappedBy = "lectures") //
    List<Category> categorys = new ArrayList<>();

    @Column(nullable = false)
    private String lectureName;

    @Column(nullable = false, length = 2000)
    private String lectureContent;


    private String lectureImg1;
    private String lectureImg2;
    private String lectureVedio;
    private String filePath;

    private String zoomUrl;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int lectureCurnum;
    @Column(columnDefinition = "INT DEFAULT 5")
    private int lectureMinnum;
    private int lectureMaxnum;


    private String startEnrolmentDate;                //수강신청일
    private String endEnrolmentDate;                  //수강종료일

    private String startStudyDate;                      //강의시작일
    private String endStudyDate;                        //강의종료일

    @Column(columnDefinition = "INT DEFAULT 1")
    private int lectureAct = 1;                               // 1.강의예정 ,2.강의진행중, 3.강의종료








}
