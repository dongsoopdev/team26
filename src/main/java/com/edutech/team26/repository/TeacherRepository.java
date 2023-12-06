package com.edutech.team26.repository;

import com.edutech.team26.domain.Teacher;
import com.edutech.team26.dto.TeacherDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByMno(Long mno);

}