package com.edutech.team26.biz;

public interface StudentService {

    boolean applyStudent(Long mno, Long lecture_no) throws Exception;

    //수강신청 취소 (delelct x -> status 'CANCEL'으로 변경)
    void deleteCourse(long lectureNo);
}