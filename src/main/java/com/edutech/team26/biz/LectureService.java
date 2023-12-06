package com.edutech.team26.biz;

import com.edutech.team26.dto.LectureDTO;
import com.edutech.team26.model.LectureParam;

import java.util.List;

public interface LectureService{


    /*
     * 강좌 등록
     */
    public boolean add(LectureDTO LectureDTO);

    /*
     * 강좌 정보 수정
     */
    boolean set(LectureDTO LectureDTO);

    /*
     * 강좌 목록
     */
    List<LectureDTO> list(LectureParam lectureParam);


    /*
     * 강좌 상세 정보
     */
    LectureDTO getById(long id);


//    /*
//     * 강좌 내용 삭제
//     */
//    boolean del(String idList);
//
//    /*
//     * 프론트 강좌 목록
//     */
//    List<LectureDTO> frontList(LectureParam lecturePara;
//
//    /*
//     * 프론트 강좌 상세 정보
//     */
//    LectureDTO frontDetail(long lecture_no);
//
//    /*
//     * 수강 신청
//     */
//    //ServiceResult req(TakeCourseInput takeCourseInput);
//
//    /*
//     * 전체 강좌 목록
//     */
//    List<LectureDTO> listAll();

}
