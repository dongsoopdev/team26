package com.edutech.team26.controller;

import com.edutech.team26.biz.LectureService;
import com.edutech.team26.domain.VwLecture;
import com.edutech.team26.dto.LectureDTO;
import com.edutech.team26.dto.MemberSecurityDTO;
import com.edutech.team26.model.LectureParam;
import com.edutech.team26.repository.VwLectureRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        VwLecture vwLecture = vwLectureRepository.getBylectureNo(lecture_no);

        model.addAttribute("lecture", vwLecture);
        //model.addAttribute("lecture", lectureDTO);
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


}
