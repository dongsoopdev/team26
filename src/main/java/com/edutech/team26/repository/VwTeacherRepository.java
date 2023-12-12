package com.edutech.team26.repository;

import com.edutech.team26.domain.VwCourse;
import com.edutech.team26.domain.VwTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VwTeacherRepository extends JpaRepository<VwTeacher, Long> {

    VwTeacher getByMno(long mno);

}