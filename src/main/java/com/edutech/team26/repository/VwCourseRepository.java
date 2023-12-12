package com.edutech.team26.repository;

import com.edutech.team26.domain.Member;
import com.edutech.team26.dto.TeacherVO;
import com.edutech.team26.domain.VwCourse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface VwCourseRepository extends JpaRepository<VwCourse, Long> {

    VwCourse getByMno(long mno);

}