package com.edutech.team26.dto;


import com.edutech.team26.domain.Category;
import com.edutech.team26.domain.Lecture;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LectureDTO {

    private long lecture_no;
    //private long teacher_no;
    //private List<Category> categorys = new ArrayList<>();
    private String lectureName;
    private String lectureContent;
    private String lectureImg1;
    private String lectureImg2;
    private String lectureVedio;
    private String filePath;
    private String zoomUrl;
    private int lectureCurnum;
    private int lectureMinnum;
    private int lectureMaxnum;
    private String startEnrolmentDate;                //수강신청일
    private String endEnrolmentDate;                  //수강종료일
    private String startStudyDate;                      //강의시작일
    private String endStudyDate;                        //강의종료일
    private int lectureAct;


    long totalCount;
    long seq;


    public static LectureDTO of(Lecture lecture) {
        return LectureDTO.builder()
                .lecture_no(lecture.getLecture_no())
                //.teacher_no(lecture.getTeacher_no())
                //.categorys(lecture.getCategorys())
                .lectureName(lecture.getLectureName())
                .lectureContent(lecture.getLectureContent())
                .lectureImg1(lecture.getLectureImg1())
                .lectureImg2(lecture.getLectureImg2())
                .lectureVedio(lecture.getLectureVedio())
                .filePath(lecture.getFilePath())
                .zoomUrl(lecture.getZoomUrl())
                .lectureCurnum(lecture.getLectureCurnum())
                .lectureMinnum(lecture.getLectureMinnum())
                .lectureMaxnum(lecture.getLectureMaxnum())
                .startEnrolmentDate(lecture.getStartEnrolmentDate())
                .endEnrolmentDate(lecture.getEndEnrolmentDate())
                .startStudyDate(lecture.getStartStudyDate())
                .endStudyDate(lecture.getEndStudyDate())
                .lectureAct(lecture.getLectureAct())
                .build();
    }

    public static List<LectureDTO> of(List<Lecture> lectureList) {

        if (lectureList == null) {
            return null;
        }

        List<LectureDTO> lectureDtoList = new ArrayList<>();
        for(Lecture x: lectureList) {
            lectureDtoList.add(LectureDTO.of(x));
        }
        return lectureDtoList;
    }


}
