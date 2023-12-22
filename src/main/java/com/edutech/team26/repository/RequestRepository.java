package com.edutech.team26.repository;

import com.edutech.team26.domain.Lecture;
import com.edutech.team26.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RequestRepository extends JpaRepository<Request, Long> {


/*
    Request findByLecture_Lecture_no(Long lectureNo);

    boolean existsByLecture_Lecture_no(Long lectureNo);
*/

    @Query("select u from Request u where u.lecture.lecture_no = :lecture_no")
    Request findByLectureNo(@Param("lecture_no") long lecture_no);


    @Query("select COUNT(*) from Request u where u.lecture.lecture_no = :lecture_no")
    Long countByLecture_No(@Param("lecture_no") long lecture_no);
}

