package com.edutech.team26.repository;

import com.edutech.team26.model.VwTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VwTeacherRepository extends JpaRepository<VwTeacher, Long> {
    VwTeacher getByMno(long mno);

}