package com.edutech.team26.repository;

import com.edutech.team26.domain.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {
    @Modifying
    @Transactional
    @Query("update Qna q set q.visited = q.visited+1 where q.qna_no = :qna_no ")
    void updateQnaByVisited(@Param("qna_no") Long qna_no);
}
