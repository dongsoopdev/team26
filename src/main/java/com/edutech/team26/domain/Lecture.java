package com.edutech.team26.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
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

    private String zoomUrl;  // 줌 URL (매일 갱신)
    private LocalDateTime zoomDate; // 줌 실행시간 (매일 갱신)

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



    public void updateZoomUrl(String zoomUrl, LocalDateTime zoomDate){
        this.zoomUrl = zoomUrl;
        this.zoomDate = zoomDate;
    }


    // 줌등록
    public void updateLectureCurnum(int lectureCurnum){
        this.lectureCurnum =lectureCurnum;
    }




    public void updateLectureAct(int type) {
        this.lectureAct = type;
    }



    public void updateLecture(long lecture_no, Teacher teacher, List<Category> categorys, String lectureName, String lectureContent, String lectureImg1, String lectureImg2, String lectureVedio, String filePath, String zoomUrl, int lectureCurnum, int lectureMinnum, int lectureMaxnum, String startEnrolmentDate, String endEnrolmentDate, String startStudyDate, String endStudyDate, int lectureAct) {
        this.teacher = teacher;
        this.categorys = categorys;
        this.lectureName = lectureName;
        this.lectureContent = lectureContent;
        this.lectureImg1 = lectureImg1;
        this.lectureImg2 = lectureImg2;
        this.lectureVedio = lectureVedio;
        this.filePath = filePath;
        this.zoomUrl = zoomUrl;
        this.lectureCurnum = lectureCurnum;
        this.lectureMinnum = lectureMinnum;
        this.lectureMaxnum = lectureMaxnum;
        this.startEnrolmentDate = startEnrolmentDate;
        this.endEnrolmentDate = endEnrolmentDate;
        this.startStudyDate = startStudyDate;
        this.endStudyDate = endStudyDate;
        this.lectureAct = lectureAct;
    }
}