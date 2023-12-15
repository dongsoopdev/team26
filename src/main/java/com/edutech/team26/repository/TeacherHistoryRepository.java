package com.edutech.team26.repository;

import com.edutech.team26.domain.Files;
import com.edutech.team26.domain.TeacherHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherHistoryRepository extends JpaRepository<TeacherHistory, Long> {

    List<TeacherHistory> findByMno(Long mno);

}
