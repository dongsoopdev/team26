package com.edutech.team26.repository;

import com.edutech.team26.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByMnoAndLectureNo(Long mno, Long lecture_no);

    List<Student> findAllByMnoAndLectureNo(Long mno, Long lecture_no);

    long countBylectureNoAndStudentNoAndStatus(long lecture_no, long mno, String status);

    //Student findByLectureNoAndStatus(long lectureNo, String status);

    Student findByLectureNoAndStatus(long lectureNo, String status);

    List<Student> findByLectureNo(long lectureNo);

    Optional<Student> findByMnoAndLectureNoAndStatus(Long mno, long lectureNo, String status);
}