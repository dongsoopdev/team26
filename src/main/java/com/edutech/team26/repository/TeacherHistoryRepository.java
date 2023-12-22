package com.edutech.team26.repository;

import com.edutech.team26.domain.TeacherHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeacherHistoryRepository extends JpaRepository<TeacherHistory, Long> {

    List<TeacherHistory> findByMnoOrderByRegDateDesc(Long mno);

    TeacherHistory findByTeacherHistoryNo(Long teacherHistoryNo);

    @Query(nativeQuery = true, value = "SELECT * FROM teacher_history WHERE teacher_history_no IN (SELECT MAX(teacher_history_no) FROM teacher_history GROUP BY mno)")
    List<TeacherHistory> findAllTeacherHistory();

}
