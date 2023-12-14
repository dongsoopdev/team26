package com.edutech.team26.biz;

import com.edutech.team26.domain.VwLecture;
import com.edutech.team26.domain.VwTeacher;
import com.edutech.team26.dto.TeacherVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TeacherService {

    boolean updateGrade(Long mno, MultipartFile uploadFile, HttpServletRequest request) throws Exception;

    boolean changeActive(Long teacherNo, int type) throws Exception;

    List<TeacherVO> teacherList();


/*    List<vwlecture> findAll();*/


    VwLecture getByMno(long mno);

}