package com.edutech.team26.controller;

import com.edutech.team26.biz.MemberService;
import com.edutech.team26.biz.NoticeService;
import com.edutech.team26.biz.StudentService;
import com.edutech.team26.domain.Member;
import com.edutech.team26.domain.Student;
import com.edutech.team26.dto.MemberDTO;
import com.edutech.team26.dto.MemberSecurityDTO;
import com.edutech.team26.dto.NoticeDTO;
import com.edutech.team26.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
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
public class NoticeController {
    private final NoticeService noticeService;
    private final MemberService memberService;
    private final StudentRepository studentRepository;

    @GetMapping("/notice/noticeList")
    public String noticeList(@RequestParam(name = "lecture_no") Long lecture_no, Model model) {
        List<NoticeDTO> noticeList = noticeService.noticeList(lecture_no);
        model.addAttribute("noticeList",noticeList);
        return "notice/noticeList";
    }

    @GetMapping("/notice/getNotice")
    public String getNotice(@RequestParam(name = "notice_no") Long notice_no, Model model) {
        NoticeDTO noticeDTO= noticeService.findByNno(notice_no);
        noticeService.updateVisited(notice_no);
        model.addAttribute("notice", noticeDTO);
        return "notice/getNotice";
    }

    @GetMapping("/notice/insertNotice")
    public String insertNotice(Model model) {
        return "notice/insertNotice";
    }

    @PostMapping("/notice/insertNotice")
    public String insertNoticePro (NoticeDTO noticeDTO) {
        noticeService.insertNotice(noticeDTO);
        return "redirect:/notice/noticeList?lecture_no="+ noticeDTO.getLecture_no();
    }

    @GetMapping("/notice/updateNotice")
    public String updateNotice(@RequestParam(name = "notice_no") Long notice_no, Model model) {
        NoticeDTO noticeDTO= noticeService.findByNno(notice_no);
        model.addAttribute("notice", noticeDTO);
        return "notice/updateNotice";
    }

    @PostMapping("/notice/updateNotice")
    public String updateNoticePro (NoticeDTO noticeDTO) {
        noticeService.updateNotice(noticeDTO);
        return "redirect:/notice/noticeList?lecture_no="+ noticeDTO.getLecture_no();
    }

    @GetMapping("/notice/deleteNotice")
    public String delete(@RequestParam(name = "notice_no") Long notice_no) {
        NoticeDTO noticeDTO= noticeService.findByNno(notice_no);
        Long lecNo = noticeDTO.getLecture_no();
        noticeService.deleteNotice(notice_no);
        return "redirect:/notice/noticeList?lecture_no="+ lecNo;
    }

    //관리자의 공지사항
    @GetMapping("/admin/noticeList")
    public String adminNoticeList( Model model) {
        List<NoticeDTO> noticeList = noticeService.findNoticeAll();
        model.addAttribute("noticeList",noticeList);
        return "admin/notice/adminNoticeList";
    }

    @GetMapping("/admin/getNotice")
    public String adminGetNotice( @RequestParam(name = "notice_no") Long notice_no, Model model) {
        NoticeDTO noticeDTO= noticeService.findByNno(notice_no);
        model.addAttribute("notice",noticeDTO);
        return "admin/notice/adminGetNotice";
    }

    @GetMapping("/admin/updateNotice")
    public String adminUpdateNotice(@RequestParam(name = "notice_no") Long notice_no, Model model) {
        NoticeDTO noticeDTO= noticeService.findByNno(notice_no);
        model.addAttribute("notice", noticeDTO);
        return "admin/notice/adminUpdateNotice";
    }

    @PostMapping("/admin/updateNotice")
    public String adminUpdateNoticePro (NoticeDTO noticeDTO) {
        noticeService.updateNotice(noticeDTO);
        return "redirect:/admin/noticeList";
    }

    @GetMapping("/admin/deleteNotice")
    public String adminDelete(@RequestParam(name = "notice_no") Long notice_no) {
        noticeService.deleteNotice(notice_no);
        return "redirect:/admin/noticeList";
    }

    //teacher의 공지사항
    @GetMapping("/teacher/noticeList")
    public String teacherNoticeList(@RequestParam(name = "lecture_no") Long lecture_no, Model model) {
        List<NoticeDTO> noticeList = noticeService.noticeList(lecture_no);
        model.addAttribute("noticeList",noticeList);
        model.addAttribute("lecture_no",lecture_no);
        return "teacher/notice/teacherNoticeList";
    }

    @GetMapping("/teacher/getNotice")
    public String teacherGetNotice( @RequestParam(name = "notice_no") Long notice_no, Model model) {
        NoticeDTO noticeDTO= noticeService.findByNno(notice_no);
        Long lecture_no = noticeDTO.getLecture_no();
        MemberDTO memberDTO = memberService.getMemberInfo(noticeDTO.getMno());
        String userName = memberDTO.getUserName();
        model.addAttribute("lecture_no",lecture_no);
        model.addAttribute("notice",noticeDTO);
        model.addAttribute("userName",userName);
        return "teacher/notice/teacherGetNotice";
    }

    @GetMapping("/teacher/insertNotice")
    public String teacherInsertNotice(@RequestParam(name = "lecture_no") Long lecture_no,Model model) {
        model.addAttribute("lecture_no",lecture_no);
        return "teacher/notice/teacherInsertNotice";
    }

    @PostMapping("/teacher/insertNotice")
    public String teacherInsertNoticePro ( NoticeDTO noticeDTO) {
        noticeService.insertNotice(noticeDTO);
        return "redirect:/teacher/noticeList?lecture_no="+ noticeDTO.getLecture_no();
    }

    @GetMapping("/teacher/updateNotice")
    public String teacherUpdateNotice(@RequestParam(name = "notice_no") Long notice_no, Model model) {
        NoticeDTO noticeDTO= noticeService.findByNno(notice_no);
        Long lecture_no = noticeDTO.getLecture_no();
        model.addAttribute("lecture_no",lecture_no);
        model.addAttribute("notice", noticeDTO);
        return "teacher/notice/teacherUpdateNotice";
    }

    @PostMapping("/teacher/updateNotice")
    public String teacherUpdateNoticePro (NoticeDTO noticeDTO) {
        noticeService.updateNotice(noticeDTO);
        return "redirect:/teacher/noticeList?lecture_no="+ noticeDTO.getLecture_no();
    }

    @GetMapping("/teacher/deleteNotice")
    public String teacherDelete(@RequestParam(name = "notice_no") Long notice_no, Model model) {
        NoticeDTO noticeDTO= noticeService.findByNno(notice_no);
        Long lecture_no = noticeDTO.getLecture_no();
        model.addAttribute("lecture_no",lecture_no);
        noticeService.deleteNotice(notice_no);
        return "redirect:/teacher/noticeList?lecture_no="+ lecture_no;
    }

    //학생의 공지사항
    @GetMapping("/student/noticeList")
    public String studentNoticeList(@RequestParam(name = "lecture_no") Long lecture_no, Model model) {
        List<NoticeDTO> noticeList = noticeService.noticeList(lecture_no);
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       /* Optional<Student> student =studentRepository.findByMnoAndLectureNo(member.getMno(), lecture_no);
        model.addAttribute("student_no",student.get().getStudentNo());*/
        Optional<Student> student =studentRepository.findByMnoAndLectureNoAndStatus(member.getMno(), lecture_no,"COMPLETE");
        if (!student.isPresent()) { student = studentRepository.findByMnoAndLectureNoAndStatus(member.getMno(),lecture_no, "REQ");}
        model.addAttribute("student_no", student.get().getStudentNo());

        model.addAttribute("noticeList",noticeList);
        model.addAttribute("lecture_no",lecture_no);
        return "student/notice/studentNoticeList";
    }
    @GetMapping("/student/getNotice")
    public String studentGetNotice( @RequestParam(name = "notice_no") Long notice_no, Model model) {
        NoticeDTO noticeDTO= noticeService.findByNno(notice_no);
        Long lecture_no = noticeDTO.getLecture_no();
        noticeService.updateVisited(notice_no);
        MemberDTO memberDTO = memberService.getMemberInfo(noticeDTO.getMno());
        String userName = memberDTO.getUserName();

        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Student> student =studentRepository.findByMnoAndLectureNo(member.getMno(), lecture_no);
        model.addAttribute("student_no",student.get().getStudentNo());
        model.addAttribute("lecture_no",lecture_no);
        model.addAttribute("notice",noticeDTO);
        model.addAttribute("userName",userName);
        model.addAttribute("lecture_no",lecture_no);
        model.addAttribute("notice",noticeDTO);
        return "student/notice/studentGetNotice";
    }
}
