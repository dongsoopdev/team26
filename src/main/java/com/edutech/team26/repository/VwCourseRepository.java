package com.edutech.team26.repository;

import com.edutech.team26.domain.VwCourse;
import com.edutech.team26.domain.VwTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VwCourseRepository extends JpaRepository<VwCourse, Long> {

    List<VwCourse> findByStudentMno(long Mno);

    //List<VwCourse> findByStudentNo(long studentNo);

    VwCourse findByStudentNo(long studentNo);
}