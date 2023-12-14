package com.edutech.team26.repository;

import com.edutech.team26.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Modifying
    @Transactional
    @Query("update Notice n set n.visited = n.visited+1 where n.notice_no = :notice_no ")
    void updateNoticeByVisited(@Param("notice_no") Long notice_no);
}
