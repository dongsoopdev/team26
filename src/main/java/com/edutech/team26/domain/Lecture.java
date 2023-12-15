package com.edutech.team26.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class Lecture extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lecture_no")
    private long lecture_no;

    //long teacher_no;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_no")
    private Teacher teacher;


    //다대다
    @ManyToMany(mappedBy = "lectures")
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
    private int lectureAct;                               // 1.강의예정 ,2.강의진행중, 3.강의종료




    public void updateLectureAct(int type) {
        // Get the current date
        //LocalDate currentDate = LocalDate.now();

        // Parse the start and end study dates
//        LocalDate startStudyLocalDate = LocalDate.parse(this.startStudyDate, DateTimeFormatter.ISO_LOCAL_DATE);
//        LocalDate endStudyLocalDate = LocalDate.parse(this.endStudyDate, DateTimeFormatter.ISO_LOCAL_DATE);
        //LocalDate startStudyLocalDate = LocalDate.parse(startStudyDate, DateTimeFormatter.ISO_LOCAL_DATE);
       //LocalDate endStudyLocalDate = LocalDate.parse(endEnrolmentDate, DateTimeFormatter.ISO_LOCAL_DATE);

        // Update lectureAct based on the conditions
        /*if (startStudyLocalDate.isAfter(currentDate)) {
            this.lectureAct=1;// 강의예정
        } else if (startStudyLocalDate.isBefore(currentDate) && endStudyLocalDate.isAfter(currentDate)) {
            this.lectureAct = 2; // 강의진행중
        } else if (endStudyLocalDate.isBefore(currentDate)) {
            this.lectureAct = 3; // 강의종료
        }*/

        this.lectureAct = type; // 강의종료
    }








}