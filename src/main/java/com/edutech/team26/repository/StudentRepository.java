package com.edutech.team26.repository;

import com.edutech.team26.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByMnoAndLectureNo(Long mno, Long lectureNo);
    long countByLectureNoAndStudentNoAndStatusIn(long lecture_no, long mno, Collection<String> statusList);

}