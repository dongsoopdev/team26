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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
   @GetMapping("lectureList")
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


        model.addAttribute("lecture", lectureDTO);
        model.addAttribute("lecture_no", lecture_no);
        return "teacher/lecture/getlecture";
    }

}
