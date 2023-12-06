package com.edutech.team26.biz;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface TeacherService {

    boolean updateGrade(Long mno, MultipartFile uploadFile, HttpServletRequest request) throws Exception;

    boolean changeActive(Long teacherNo, int type) throws Exception;

}