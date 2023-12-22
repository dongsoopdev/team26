package com.edutech.team26.controller;

import com.edutech.team26.biz.LectureService;
import com.edutech.team26.domain.Lecture;
import com.edutech.team26.domain.Request;
import com.edutech.team26.domain.VwLecture;
import com.edutech.team26.dto.LectureDTO;
import com.edutech.team26.dto.MemberSecurityDTO;
import com.edutech.team26.dto.NoticeDTO;
import com.edutech.team26.dto.RequestDTO;
import com.edutech.team26.model.LectureParam;
import com.edutech.team26.repository.RequestRepository;
import com.edutech.team26.repository.VwLectureRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/teacher")
public class TeacherController {


    @Autowired
    private LectureService lectureService;

    @Autowired
    private VwLectureRepository vwLectureRepository;

    @Autowired
    private RequestRepository requestRepository;


    @GetMapping("/home")
    public String teacherHome(Model model) {
        log.info("---------------------- TEACHER ----------------------");
        return "teacher/home";
    }



    // ===========================================   [강의 관련 시작]  ============================================================

    // 개설 강좌 전체 리스트
   @GetMapping("/lectureList")
    public String list(Model model, LectureParam lectureParam) {
       MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       System.out.println(member.getMno());

        List<VwLecture> lectureList = vwLectureRepository.findByMno(member.getMno());

        model.addAttribute("lectureList", lectureList);


        return "teacher/lecture/lectureList";
    }

    // 선택한 강의 상세보기
    @GetMapping("/getlecture/{lecture_no}")
    public String getProduct(@PathVariable("lecture_no") long lecture_no, Model model) {

        //상품 정보
        LectureDTO lectureDTO = lectureService.getById(lecture_no);
        System.out.println(lectureDTO);

        boolean requestExists = lectureService.requestExistsForLecture(lecture_no);
        model.addAttribute("requestExists", requestExists);


        VwLecture vwLecture = vwLectureRepository.getBylectureNo(lecture_no);

        model.addAttribute("lecture", vwLecture);
        model.addAttribute("lecture_no", lecture_no);
        return "teacher/lecture/getlecture";
    }



    // addform 불러오기
    @GetMapping("/addZoom")
    public String addZoomForm(Model model, @RequestParam("lecture_no") long lecture_no) {
        VwLecture vwLecture = vwLectureRepository.getBylectureNo(lecture_no);
        model.addAttribute("lecture", vwLecture);
        model.addAttribute("lecture_no",lecture_no);

        return "teacher/lecture/addZoom";
    }

    //zoomurl추가
    @PostMapping("/addZoom")
    public String addZoomSubmit(Model model, @RequestParam("zoomUrl") String zoomUrl, @RequestParam("lecture_no") long lecture_no, @RequestParam("zoomDate")LocalDateTime zoomDate){
        
        lectureService.addZoomUrl(zoomUrl, lecture_no, zoomDate);
        model.addAttribute("lecture_no",lecture_no);

        return "teacher/lecture/entranceZoom";
    }

    // zoom강의 입장
    @GetMapping("/entranceZoom")
    public String entranceZoom(Model model, @RequestParam("lecture_no") long lecture_no) {
        VwLecture vwLecture = vwLectureRepository.getBylectureNo(lecture_no);
        model.addAttribute("lecture", vwLecture);
        model.addAttribute("lecture_no",lecture_no);

        return "teacher/lecture/entranceZoom";
    }


    //관리자 문의 form
    @GetMapping("/requestLecture/{lecture_no}")
    public String requestLecture(Model model, @PathVariable("lecture_no") long lecture_no) {
        VwLecture vwLecture = vwLectureRepository.getBylectureNo(lecture_no);
        model.addAttribute("lecture", vwLecture);
        model.addAttribute("lecture_no",lecture_no);

        return "teacher/lecture/requestLecture";
    }


    //관리자 문의
    @PostMapping(value = {"/requestLecture"})
    public String saveSubmit(Model model, RequestDTO requestDTO) throws IOException {
        Request request = requestRepository.findByLectureNo(requestDTO.getLecture_no());
        model.addAttribute("request",request);


        lectureService.addRequest(requestDTO);

        return "redirect:/teacher/getlecture/" + requestDTO.getLecture_no();
    }


    //관리자 문의 확인하기
    @GetMapping("/viewRequest/{lecture_no}")
    public String viewRequest(@PathVariable(name = "lecture_no") Long lecture_no, Model model) {
        Request viewRequest = requestRepository.findByLectureNo(lecture_no);
        model.addAttribute("request",viewRequest);

        VwLecture vwLecture = vwLectureRepository.getBylectureNo(lecture_no);
        String lectureName = vwLecture.getLectureName();
        model.addAttribute("lectureName",lectureName);

        return "teacher/lecture/viewRequest";
    }

}
