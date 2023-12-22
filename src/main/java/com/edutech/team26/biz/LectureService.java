package com.edutech.team26.biz;


import com.edutech.team26.domain.VwLecture;
import com.edutech.team26.dto.LectureDTO;
import com.edutech.team26.dto.RequestDTO;
import com.edutech.team26.dto.StudentDTO;

import com.edutech.team26.dto.TeacherVO;
import com.edutech.team26.model.LectureParam;
import com.edutech.team26.model.ServiceResult;
import com.edutech.team26.domain.VwCourse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface LectureService {


    /*
     * 강좌 등록
     */
    void addLecture(LectureDTO lectureDTO, MultipartFile[] file, HttpServletRequest request) throws IOException;

    /*
     * 강좌 정보 수정
     */

    void updateLecture(LectureDTO lectureDTO, Long lecture_no, MultipartFile[] imgFiles) throws IOException;

    /*
     * 강좌 목록
     */
    List<LectureDTO> list(LectureParam lectureParam);

    List<VwLecture> findAll();

    List<VwLecture> vwFindAll();

    /*
     * 강좌 상세 정보
     */
    LectureDTO getById(long id);


    //강의 철회 (delelct x -> lecture_act 5번으로 변경)
    void deleteLecture(long lectureNo);

    //강의 철회 취소
    void deleteCancleLecture(long lectureNo);

    // 수강취소시 수강인원 변경
    void deleteCourse(long lectureNo);


    // 수강 신청
    ServiceResult apply(StudentDTO studentDTO);


    // 강의 이름 중복 확인
    boolean isLectureNameDuplicate(String lectureName);


    //zoom url emdfhr
    void addZoomUrl(String zoomUrl, long lectureNo, LocalDateTime zoomDate);


    void addRequest(RequestDTO requestDTO);





    boolean requestExistsForLecture(Long lectureNo);
}