package com.edutech.team26.biz;

import com.edutech.team26.dto.LectureDTO;
import com.edutech.team26.model.VwTeacher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TeacherService {

    boolean updateGrade(Long mno, MultipartFile uploadFile, HttpServletRequest request) throws Exception;

    boolean changeActive(Long teacherNo, int type) throws Exception;

    List<VwTeacher> findAll();
    VwTeacher getByMno(long mno);

}