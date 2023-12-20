package com.edutech.team26.controller;


import com.edutech.team26.biz.LectureService;
import com.edutech.team26.biz.StudentService;
import com.edutech.team26.domain.Lecture;
import com.edutech.team26.domain.Student;
import com.edutech.team26.domain.VwCourse;

import com.edutech.team26.domain.VwLecture;
import com.edutech.team26.dto.MemberSecurityDTO;

import com.edutech.team26.model.LectureParam;

import com.edutech.team26.repository.LectureRepository;
import com.edutech.team26.repository.StudentRepository;
import com.edutech.team26.repository.VwCourseRepository;
import com.edutech.team26.repository.VwLectureRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private LectureService lectureService;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private VwCourseRepository vwCourseRepository;
    @Autowired
    private VwLectureRepository vwLectureRepository;



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
        model.addAttribute("lecture_no", course.getLectureNo());
        return "student/course/getCourse";
    }



    //수강 취소하기(삭제 x)
    @GetMapping("/deleteCourse/{student_no}")
    public String deleteLecture(@PathVariable("student_no") long student_no, Model model) {


        studentService.deleteCourse(student_no); // 수강내역 삭제
        VwCourse course = vwCourseRepository.findByStudentNo(student_no);
        Lecture lecture = lectureRepository.getById(course.getLectureNo());
        lectureService.deleteCourse(lecture.getLecture_no()); // 수강인원 1명 삭제


        return "redirect:/student/courseList";

    }


    @GetMapping("/entranceZoom")
    public String entranceZoom(Model model, @RequestParam("lecture_no") long lecture_no) {

       VwLecture vwLecture =vwLectureRepository.getBylectureNo(lecture_no);
        VwCourse vwCourse = vwCourseRepository.findByLectureNo(vwLecture.getLectureNo());

        model.addAttribute("lecture", vwCourse);
        model.addAttribute("lecture_no",lecture_no);


        // 사이드 메뉴 때문에 "student_no" 던져줘야 해서 생성
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Student> student =studentRepository.findByMnoAndLectureNo(member.getMno(), lecture_no);
        model.addAttribute("student_no",student.get().getStudentNo());
  

        return "student/course/entranceZoom";

    }


    // This method handles the entry action
    @GetMapping("/entranceLecture")
    @ResponseBody
    public String enterLecture(@RequestParam Long student_no) {

        studentService.updateEntranceStatus(student_no);

        return "Success";
    }



}
