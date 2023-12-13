package com.edutech.team26.controller;


import com.edutech.team26.biz.StudentService;
import com.edutech.team26.domain.Student;
import com.edutech.team26.domain.VwCourse;

import com.edutech.team26.dto.MemberSecurityDTO;

import com.edutech.team26.model.LectureParam;

import com.edutech.team26.repository.VwCourseRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private VwCourseRepository vwCourseRepository;

    @GetMapping("/home")
    public String studentHome(Model model) {
        log.info("---------------------- STUDENT ----------------------");
        return "student/home";
    }

    //수강신청 목록
    @GetMapping("/courseList")
    public String list(Model model, LectureParam lectureParam) {
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(member.getMno());

        List<VwCourse> courseList = vwCourseRepository.findByStudentMno(member.getMno());

        System.out.println("전체 강좌" + courseList.toString());
        model.addAttribute("courseList", courseList);

        return "student/course/courseList";
    }


    // 선택한 강의 상세보기
    @GetMapping("/getCourse/{student_no}")
    public String getProduct(@PathVariable("student_no") long student_no, Model model) {

        VwCourse course = vwCourseRepository.findByStudentNo(student_no);
        System.out.println(course);

        model.addAttribute("student_no", student_no);
        model.addAttribute("course", course);
        return "student/course/getCourse";
    }

}
