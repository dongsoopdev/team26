package com.edutech.team26.controller;

import com.edutech.team26.biz.MemberService;
import com.edutech.team26.biz.QnaService;
import com.edutech.team26.biz.StudentService;
import com.edutech.team26.domain.Student;
import com.edutech.team26.dto.MemberDTO;
import com.edutech.team26.dto.MemberSecurityDTO;
import com.edutech.team26.dto.QnaDTO;
import com.edutech.team26.dto.QnaDTO;
import com.edutech.team26.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
@RequiredArgsConstructor
public class QnaController {
    private final QnaService qnaService;
    private final MemberService memberService;
    private final StudentRepository studentRepository;

    @GetMapping("/qna/qnaList")
    public String qnaList(@RequestParam(name = "lecture_no") Long lecture_no, Model model) {
        List<QnaDTO> qnaList = qnaService.qnaListByLec(lecture_no);
        model.addAttribute("qnaList",qnaList);
        return "qna/qnaList";
    }

    @GetMapping("/qna/getQna")
    public String getQna(@RequestParam(name = "qna_no") Long qna_no, Model model) {
        QnaDTO qnaDTO= qnaService.findByQno(qna_no);
        qnaService.updateVisited(qna_no);
        model.addAttribute("qna", qnaDTO);
        return "qna/getQna";
    }

    @GetMapping("/qna/insertQna")
    public String insertQna(Model model) {
        return "qna/insertQna";
    }

    @PostMapping("/qna/insertQna")
    public String insertQnaPro (QnaDTO qnaDTO) {
        qnaService.insertQna(qnaDTO);
        return "redirect:/qna/qnaList?lecture_no="+ qnaDTO.getLecture_no();
    }

    @GetMapping("/qna/updateQna")
    public String updateQna(@RequestParam(name = "qna_no") Long qna_no, Model model) {
        QnaDTO qnaDTO= qnaService.findByQno(qna_no);
        model.addAttribute("qna", qnaDTO);
        return "qna/updateQna";
    }

    @PostMapping("/qna/updateQna")
    public String updateQnaPro (QnaDTO qnaDTO) {
        qnaService.updateQna(qnaDTO);
        return "redirect:/qna/qnaList?lecture_no="+ qnaDTO.getLecture_no();
    }

    @GetMapping("/qna/deleteQna")
    public String delete(@RequestParam(name = "qna_no") Long qna_no) {
        QnaDTO qnaDTO= qnaService.findByQno(qna_no);
        Long lecNo = qnaDTO.getLecture_no();
        qnaService.deleteQna(qna_no);
        return "redirect:/qna/qnaList?lecture_no="+ lecNo;
    }

    //관리자의 Qna
    @GetMapping("/admin/qnaList")
    public String adminQnaList( Model model) {
        List<QnaDTO> qnaList = qnaService.findQnaAll();
        model.addAttribute("qnaList",qnaList);
        return "admin/qna/adminQnaList";
    }

    @GetMapping("/admin/getQna")
    public String adminGetQna( @RequestParam(name = "qna_no") Long qna_no, Model model) {
        QnaDTO qnaDTO= qnaService.findByQno(qna_no);
        model.addAttribute("qna",qnaDTO);
        return "admin/qna/adminGetQna";
    }

    @GetMapping("/admin/updateQna")
    public String adminUpdateQna(@RequestParam(name = "qna_no") Long qna_no, Model model) {
        QnaDTO qnaDTO= qnaService.findByQno(qna_no);
        model.addAttribute("qna", qnaDTO);
        return "admin/qna/adminUpdateQna";
    }

    @PostMapping("/admin/updateQna")
    public String adminUpdateQnaPro (QnaDTO qnaDTO) {
        qnaService.updateQna(qnaDTO);
        return "redirect:/admin/qnaList";
    }

    @GetMapping("/admin/deleteQna")
    public String adminDelete(@RequestParam(name = "qna_no") Long qna_no) {
        qnaService.deleteQna(qna_no);
        return "redirect:/admin/qnaList";
    }

    //teacher의 Qna
    @GetMapping("/teacher/qnaList")
    public String teacherQnaList(@RequestParam(name = "lecture_no") Long lecture_no, Model model) {
        List<QnaDTO> qnaList = qnaService.qnaListByLec(lecture_no);
        model.addAttribute("qnaList",qnaList);
        model.addAttribute("lecture_no",lecture_no);
        return "teacher/qna/teacherQnaList";
    }

    @GetMapping("/teacher/getQna")
    public String teacherGetQna( @RequestParam(name = "qna_no") Long qna_no, Model model) {
        QnaDTO qnaDTO= qnaService.findByQno(qna_no);
        Long lecture_no = qnaDTO.getLecture_no();
        MemberDTO memberDTO = memberService.getMemberInfo(qnaDTO.getMno());
        String userName = memberDTO.getUserName();
        model.addAttribute("lecture_no",lecture_no);
        model.addAttribute("qna",qnaDTO);
        model.addAttribute("userName",userName);
        return "teacher/qna/teacherGetQna";
    }

    @GetMapping("/teacher/insertQna")
    public String teacherInsertQna(@RequestParam(name = "lecture_no") Long lecture_no,Model model) {
        model.addAttribute("lecture_no",lecture_no);
        return "teacher/qna/teacherInsertQna";
    }

    @PostMapping("/teacher/insertQna")
    public String teacherInsertQnaPro ( QnaDTO qnaDTO) {
        qnaService.insertQna(qnaDTO);
        return "redirect:/teacher/qnaList?lecture_no="+ qnaDTO.getLecture_no();
    }

    @GetMapping("/teacher/updateQna")
    public String teacherUpdateQna(@RequestParam(name = "qna_no") Long qna_no, Model model) {
        QnaDTO qnaDTO= qnaService.findByQno(qna_no);
        Long lecture_no = qnaDTO.getLecture_no();
        model.addAttribute("lecture_no",lecture_no);
        model.addAttribute("qna", qnaDTO);
        return "teacher/qna/teacherUpdateQna";
    }

    @PostMapping("/teacher/updateQna")
    public String teacherUpdateQnaPro (QnaDTO qnaDTO) {
        qnaService.updateQna(qnaDTO);
        return "redirect:/teacher/qnaList?lecture_no="+ qnaDTO.getLecture_no();
    }

    @GetMapping("/teacher/deleteQna")
    public String teacherDelete(@RequestParam(name = "qna_no") Long qna_no, Model model) {
        QnaDTO qnaDTO= qnaService.findByQno(qna_no);
        Long lecture_no = qnaDTO.getLecture_no();
        model.addAttribute("lecture_no",lecture_no);
        qnaService.deleteQna(qna_no);
        return "redirect:/teacher/qnaList?lecture_no="+ lecture_no;
    }

    //학생의 Qna
    @GetMapping("/student/qnaList")
    public String studentQnaList(@RequestParam(name = "lecture_no") Long lecture_no, Model model) {
        List<QnaDTO> qnaList = qnaService.qnaListByLec(lecture_no);
        model.addAttribute("qnaList",qnaList);
        model.addAttribute("lecture_no",lecture_no);

        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Student> student =studentRepository.findByMnoAndLectureNo(member.getMno(), lecture_no);
        model.addAttribute("student_no",student.get().getStudentNo());
        return "student/qna/studentQnaList";
    }
    @GetMapping("/student/getQna")
    public String studentGetQna( @RequestParam(name = "qna_no") Long qna_no, Model model) {
        QnaDTO qnaDTO= qnaService.findByQno(qna_no);
        Long lecture_no = qnaDTO.getLecture_no();
        qnaService.updateVisited(qna_no);
        MemberDTO memberDTO = memberService.getMemberInfo(qnaDTO.getMno());
        String userName = memberDTO.getUserName();
        model.addAttribute("lecture_no",lecture_no);
        model.addAttribute("qna",qnaDTO);
        model.addAttribute("userName",userName);
        model.addAttribute("lecture_no",lecture_no);
        model.addAttribute("qna",qnaDTO);

        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Student> student =studentRepository.findByMnoAndLectureNo(member.getMno(), lecture_no);
        model.addAttribute("student_no",student.get().getStudentNo());
        return "student/qna/studentGetQna";
    }

    @GetMapping("/student/insertQna")
    public String studentInsertQna(@RequestParam(name = "lecture_no") Long lecture_no,Model model) {
        model.addAttribute("lecture_no",lecture_no);

        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Student> student =studentRepository.findByMnoAndLectureNo(member.getMno(), lecture_no);
        model.addAttribute("student_no",student.get().getStudentNo());
        return "student/qna/studentInsertQna";
    }

    @PostMapping("/student/insertQna")
    public String studentInsertQnaPro ( QnaDTO qnaDTO) {
        qnaService.insertQna(qnaDTO);
        return "redirect:/student/qnaList?lecture_no="+ qnaDTO.getLecture_no();
    }

    @GetMapping("/student/updateQna")
    public String studentUpdateQna(@RequestParam(name = "qna_no") Long qna_no, Model model) {
        QnaDTO qnaDTO= qnaService.findByQno(qna_no);
        Long lecture_no = qnaDTO.getLecture_no();
        model.addAttribute("lecture_no",lecture_no);
        model.addAttribute("qna", qnaDTO);

        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Student> student =studentRepository.findByMnoAndLectureNo(member.getMno(), lecture_no);
        model.addAttribute("student_no",student.get().getStudentNo());
        return "student/qna/studentUpdateQna";
    }

    @PostMapping("/student/updateQna")
    public String studentUpdateQnaPro (QnaDTO qnaDTO) {
        qnaService.updateQna(qnaDTO);
        return "redirect:/student/qnaList?lecture_no="+ qnaDTO.getLecture_no();
    }

    @GetMapping("/student/deleteQna")
    public String studentDelete(@RequestParam(name = "qna_no") Long qna_no, Model model) {
        QnaDTO qnaDTO= qnaService.findByQno(qna_no);
        Long lecture_no = qnaDTO.getLecture_no();
        model.addAttribute("lecture_no",lecture_no);
        qnaService.deleteQna(qna_no);

        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Student> student =studentRepository.findByMnoAndLectureNo(member.getMno(), lecture_no);
        model.addAttribute("student_no",student.get().getStudentNo());
        return "redirect:/student/qnaList?lecture_no="+ lecture_no;
    }
}
