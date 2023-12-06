package com.edutech.team26.repository;

import com.edutech.team26.domain.Category;

import com.edutech.team26.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

}
