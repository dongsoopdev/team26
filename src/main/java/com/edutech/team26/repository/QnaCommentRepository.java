package com.edutech.team26.repository;

import com.edutech.team26.domain.QnaComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaCommentRepository extends JpaRepository<QnaComment, Long> {
}
