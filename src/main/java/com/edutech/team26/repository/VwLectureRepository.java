package com.edutech.team26.repository;

import com.edutech.team26.domain.VwLecture;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VwLectureRepository extends JpaRepository<VwLecture, Long> {

    VwLecture getBylectureNo(long lectureNo);
    List<VwLecture> findByMno(long teacherMno);

    List<VwLecture> findAllByOrderByLecRegDateDesc();// 최신 등록순


    List<VwLecture> findByLectureAct(int lectureAct);
}