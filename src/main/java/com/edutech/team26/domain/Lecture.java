package com.edutech.team26.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lecture extends BaseEntity{
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lecture_id;

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


    private LocalDateTime startEnrolmentDate;                //수강신청일
    private LocalDateTime endEnrolmentDate;                  //수강종료일

    private LocalDateTime startStudyDate;                      //강의시작일
    private LocalDateTime endStudyDate;                        //강의종료일

    @Column(columnDefinition = "INT DEFAULT 1")
    private int lectureAct;                               // 1.강의예정 ,2.강의진행중, 3.강의종료








}
