package com.edutech.team26.repository;

import com.edutech.team26.domain.VwCourse;
import com.edutech.team26.domain.VwTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VwCourseRepository extends JpaRepository<VwCourse, Long> {

    //학생이 수강한 강의 전체 리스트
    List<VwCourse> findByStudentMno(long mno);

    //List<VwCourse> findByStudentNo(long studentNo);

    // 학생이 수강한 강의 중 선택한 강의 상세 정보
    VwCourse findByStudentNo(long studentNo);


    //학생이 이미 수강한 강의 인지 체크
    //int countByLectureNoAndMno(long lectureno, long mno);



    // 학생이 이미 수강한 강의 중 'REQ' 상태인 강의 수 카운트
    int countByLectureNoAndMnoAndStudentStatus(long lectureno, long mno, String studentStatus);

    VwCourse findByLectureNo(long lectureNo);

}