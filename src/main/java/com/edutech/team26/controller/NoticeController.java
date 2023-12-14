package com.edutech.team26.controller;

import com.edutech.team26.biz.NoticeService;
import com.edutech.team26.dto.NoticeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/notice/noticeList")
    public String noticeList(@RequestParam(name = "lecture_no") Long lecture_no, Model model) {
        List<NoticeDTO> noticeList = noticeService.noticeList(lecture_no);
        model.addAttribute("noticeList",noticeList);
        return "notice/noticeList";
    }
    @GetMapping("/admin/noticeList")
    public String adminNoticeList(@RequestParam(name = "lecture_no") Long lecture_no, Model model) {
        List<NoticeDTO> noticeList = noticeService.noticeList(lecture_no);
        model.addAttribute("noticeList",noticeList);
        return "notice/noticeList";
    }
    @GetMapping("/teacher/noticeList")
    public String TeacherNoticeList(@RequestParam(name = "lecture_no") Long lecture_no, Model model) {
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
}
