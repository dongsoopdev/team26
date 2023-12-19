package com.edutech.team26.repository;

import com.edutech.team26.domain.TeacherHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeacherHistoryRepository extends JpaRepository<TeacherHistory, Long> {

    List<TeacherHistory> findByMnoOrderByRegDateDesc(Long mno);

    TeacherHistory findByTeacherHistoryNo(Long teacherHistoryNo);

    @Query(nativeQuery = true, value = "SELECT result.* FROM (SELECT * FROM teacher_history ORDER BY teacher_history_no DESC LIMIT 1) AS result GROUP BY result.mno")
    List<TeacherHistory> findAllTeacherHistory();

}
