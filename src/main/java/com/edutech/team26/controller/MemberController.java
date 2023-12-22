package com.edutech.team26.controller;

import com.edutech.team26.biz.MemberService;
import com.edutech.team26.biz.StudentService;
import com.edutech.team26.biz.TeacherService;
import com.edutech.team26.constant.AcceptCode;
import com.edutech.team26.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final StudentService studentService;

    private final TeacherService teacherService;

    // Member
    @GetMapping("/joinTerm")
    public String joinTerm(Model model){
        return "member/joinTerm";
    }

    @GetMapping("/join")
    public String joinUser(Model model){
        model.addAttribute("memberJoinDTO", new MemberJoinDTO());
        return "member/join";
    }

    @GetMapping("/joinFinish")
    public String joinFinish(Model model){
        return "member/joinComplete";
    }

    @PostMapping(value = "/memberDup")
    @ResponseBody
    public boolean memberDupValidation(@RequestBody MemberJoinDTO data) throws Exception {
        boolean result = memberService.memberDupValidation(data.getEmail());
        return result;
    }

    @PostMapping("/join")
    public String joinPOST(@Valid MemberJoinDTO memberJoinDTO, BindingResult bindingResult, Model model) throws Exception {
        if(bindingResult.hasErrors()){
            return "member/join";
        }
        try {
            memberService.join(memberJoinDTO);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/join";
        }
        return "redirect:/joinFinish";
    }

    @GetMapping("/login")
    public String loginGet(){
        return "member/login";
    }

    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.<br />회원가입을 하셨다면 이메일을 확인해주세요.");
        return "member/login";
    }

    @GetMapping("/loginPro")
    public String loginPro() throws Exception{
        memberService.updateLoginDate();
        return "redirect:/";
    }

    @GetMapping("/member/email-auth/{id}")
    public String emailAuth(@PathVariable(required = false) String id, Model model){
        boolean result = memberService.emailAuth(id);
        model.addAttribute("result", result);

        return "member/joinEmailComplete";
    }

    @GetMapping("/findId")
    public String findIdGet(){
        return "member/findId";
    }

    @PostMapping("/findId")
    public String findIdPost(HttpServletRequest request, RedirectAttributes rttr, Model model){
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        MemberDTO memberDTO = memberService.findId(username, phone);
        if(memberDTO != null) {
            rttr.addFlashAttribute("email", memberDTO.getEmail());
            return "redirect:/findIdFinish";
        } else {
            rttr.addFlashAttribute("error", "다시 한번 확인해주세요.");
            return "redirect:/findId";
        }
    }

    @GetMapping("/findIdFinish")
    public String findIdFinishGet(){
        return "member/findIdFinish";
    }

    @GetMapping("/findPw")
    public String findPwGet(){
        return "member/findPw";
    }

    @PostMapping("/findPw")
    public String findPwPost(HttpServletRequest request, RedirectAttributes rttr){
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        memberService.findPw(email, phone, username);
        return "redirect:/findPw";
    }

    @GetMapping("/member/resetPassword")
    public String resetPwAuth(@RequestParam("userId") Long id, @RequestParam("key") String key, Model model){
        boolean result = memberService.resetPwAuth(id, key);

        if(result) {
            model.addAttribute("userId", id);
            model.addAttribute("key", key);
        }
        model.addAttribute("result", result);

        return "member/resetPw";
    }

    @PostMapping("/member/resetPassword")
    public String resetPwAuthPro(HttpServletRequest request, Model model){
        Long id = Long.valueOf(request.getParameter("userId"));
        String key = request.getParameter("key");
        String password = request.getParameter("password");
        boolean result = memberService.resetPwAuthPro(id, key, password);
        if(result) {
            return "redirect:/login";
        } else {
            return "redirect:/member/resetPassword?userId=" + id + "&key=" + key;
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/myPage")
    public String myPage(Model model) {
        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberDTO memberDTO = memberService.getMemberInfo(memberSecurityDTO.getMno());
        model.addAttribute("memberDTO", memberDTO);
        return "member/myPage";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT')")
    @GetMapping("/myPage")
    public String myPageLms(Model model) {
        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberDTO memberDTO = memberService.getMemberInfo(memberSecurityDTO.getMno());
        model.addAttribute("memberDTO", memberDTO);
        return "member/lmsMyPage";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/myPageModify")
    public String myPageInfo(Model model) {
        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberDTO memberDTO = memberService.getMemberInfo(memberSecurityDTO.getMno());
        model.addAttribute("memberDTO", memberDTO);
        return "member/myPageInfo";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT')")
    @GetMapping("/myPageModify")
    public String myPageInfoLms(Model model) {
        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberDTO memberDTO = memberService.getMemberInfo(memberSecurityDTO.getMno());
        model.addAttribute("memberDTO", memberDTO);
        return "member/lmsMyPageInfo";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT', 'USER')")
    @PostMapping("/myPage/modifyInfo")
    public String myPageInfoPOST(@Valid MemberDTO memberDTO, BindingResult bindingResult, Model model, HttpServletRequest request, RedirectAttributes rttr) throws Exception {
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(bindingResult.hasErrors()){
            return "member/myPageInfo";
        }
        try {
            memberService.modifyInfo(member.getMno(), memberDTO);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/myPageInfo";
        }

        rttr.addFlashAttribute("success", "success");
        return "redirect:" + request.getHeader("referer");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/myPage/resetPw")
    public String myPagePw(Model model) {
        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberDTO memberDTO = memberService.getMemberInfo(memberSecurityDTO.getMno());
        MemberPwDTO memberPwDTO = new MemberPwDTO();
        memberPwDTO.setPassword(memberDTO.getPassword());
        model.addAttribute("memberPwDTO", memberPwDTO);
        return "member/myPagePw";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT')")
    @GetMapping("/myPageResetPw")
    public String myPagePwLms(Model model) {
        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberDTO memberDTO = memberService.getMemberInfo(memberSecurityDTO.getMno());
        MemberPwDTO memberPwDTO = new MemberPwDTO();
        memberPwDTO.setPassword(memberDTO.getPassword());
        model.addAttribute("memberPwDTO", memberPwDTO);
        return "member/lmsMyPagePw";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT', 'USER')")
    @PostMapping("/myPage/resetPw")
    public String myPagePwPro(@Valid MemberPwDTO memberPwDTO, BindingResult bindingResult, Model model, HttpServletRequest request, RedirectAttributes rttr) {
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(bindingResult.hasErrors()){
            return "member/myPagePw";
        }

        try {
            if(!memberPwDTO.getNewPassword().equals(memberPwDTO.getNewPasswordConfirm())) {
                model.addAttribute("errorMessage", "새로운 비밀번호와 새로운 비밀번호 확인이 일치하지 않습니다");
            } else {
                boolean modifyPwCheck = memberService.modifyPw(member.getMno(), memberPwDTO);
                if(!modifyPwCheck) {
                    model.addAttribute("errorMessage", "기존 비밀번호가 다릅니다.");
                    return "member/myPagePw";
                }
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/myPagePw";
        }

        rttr.addFlashAttribute("success", "success");
        return "redirect:" + request.getHeader("referer");
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT', 'USER')")
    @GetMapping("/myPage/withdrawUser")
    public String withdrawUser() throws Exception {
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        memberService.withdraw(member.getMno());
        return "redirect:/logout";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'USER')")
    @GetMapping("/myPage/teacherApply")
    public String teacherApply(Model model) throws Exception {
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TeacherHistoryFilesVO> teacherHistoryList = teacherService.getHistoryList(member.getMno());
        model.addAttribute("teacherHistoryList", teacherHistoryList);
        boolean canApply = true;
        boolean finishApply = false;
        for(TeacherHistoryFilesVO teacherHistoryFilesVO : teacherHistoryList) {
            if(teacherHistoryFilesVO.getStatus().equals("승인 완료")) {
                finishApply = true;
                break;
            }

            if(teacherHistoryFilesVO.getStatus().equals("신청 대기")) {
                canApply = false;
                break;
            }
        }
        model.addAttribute("canApply", canApply);
        model.addAttribute("finishApply", finishApply);
        return "member/teacherApply";
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/myTeacherApply")
    public String teacherApplyLms(Model model) throws Exception {
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TeacherHistoryFilesVO> teacherHistoryList = teacherService.getHistoryList(member.getMno());
        model.addAttribute("teacherHistoryList", teacherHistoryList);
        boolean canApply = true;
        boolean finishApply = false;
        for(TeacherHistoryFilesVO teacherHistoryFilesVO : teacherHistoryList) {
            if(teacherHistoryFilesVO.getStatus().equals("승인 완료")) {
                finishApply = true;
                break;
            }

            if(teacherHistoryFilesVO.getStatus().equals("신청 대기")) {
                canApply = false;
                break;
            }
        }
        model.addAttribute("canApply", canApply);
        model.addAttribute("finishApply", finishApply);
        return "member/lmsMyTeacherApply";
    }

    @PostMapping("/myPage/teacherApply")
    public String teacherApplyPro(HttpServletRequest request, List<MultipartFile> uploadFiles) throws Exception {
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        teacherService.applyGrade(member.getMno(), uploadFiles, request);
        return "redirect:/myPage/teacherApply";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'USER')")
    @GetMapping("/myPage/teacherApplyDetail/{fileHistoryNo}")
    public String teacherApplyDetail(@PathVariable(required = false) Long fileHistoryNo, Model model) throws Exception {
        TeacherHistoryFilesVO teacherHistory = teacherService.getHistoryDetail(fileHistoryNo);
        model.addAttribute("teacherHistory", teacherHistory);
        return "member/teacherApplyDetail";
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/myTeacherApplyDetail/{fileHistoryNo}")
    public String teacherApplyDetailLms(@PathVariable(required = false) Long fileHistoryNo, Model model) throws Exception {
        TeacherHistoryFilesVO teacherHistory = teacherService.getHistoryDetail(fileHistoryNo);
        model.addAttribute("teacherHistory", teacherHistory);
        return "member/lmsMyTeacherApplyDetail";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'USER')")
    @GetMapping("/myPage/teacherApplyCancel/{teacherHistoryNo}")
    public String teacherApplyCancel(@PathVariable(required = false) Long teacherHistoryNo, HttpServletRequest request,  Model model) throws Exception {
        teacherService.historyRemove(request, teacherHistoryNo);
        return "redirect:/myPage/teacherApply";
    }

    // 리캡챠 부분
    @PostMapping("/valid-recaptcha")
    public @ResponseBody String validRecaptcha(HttpServletRequest request){
        String result = null;
        String response = request.getParameter("g-recaptcha-response");
        boolean isRecaptcha = memberService.verifyRecaptcha(response); //인증 메소드 서비스로 분리

        if(isRecaptcha) {
            result = "success";
        }else {
            result = "false";
        }
        return result;
    }

    // 관리자 페이지
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/teacherApplyList")
    public String adminTeacherApplyList(Model model) throws Exception {
        List<TeacherHistoryVO> teacherHistoryList = teacherService.getHistoryAllList();
        model.addAttribute("teacherHistoryList", teacherHistoryList);
        return "admin/member/teacherApplyList";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/teacherApplyDetail/{id}")
    public String adminTeacherApplyDetail(@PathVariable(required = false) Long id, Model model) throws Exception {
        List<TeacherHistoryFilesVO> teacherHistoryList = teacherService.getHistoryList(id);
        model.addAttribute("teacherHistoryList", teacherHistoryList);
        return "admin/member/teacherApplyDetail";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/teacherApplyFileDetail/{id}")
    public String adminTeacherApplyFileDetail(@PathVariable(required = false) Long id, Model model) throws Exception {
        TeacherHistoryFilesVO teacherHistoryFilesVO = teacherService.getHistoryDetail(id);
        model.addAttribute("teacherHistoryFilesVO", teacherHistoryFilesVO);
        model.addAttribute("mno", teacherHistoryFilesVO.getMno());
        return "admin/member/teacherApplyFileDetail";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/teacherApplyFileDetail")
    public String adminTeacherApplyFileDetailPro(HttpServletRequest request, Model model) throws Exception {

        Integer statusNum = Integer.parseInt(request.getParameter("status"));
        Long teacherHistoryNo = Long.parseLong(request.getParameter("teacherHistoryNo"));
        Long mno = Long.parseLong(request.getParameter("mno"));
        String reason = request.getParameter("reason") != null ? request.getParameter("reason") : "";

        String status = "";
        switch (statusNum) {
            case 1:
                status = AcceptCode.ACCEPT_STATUS_OK;
                break;
            case 2:
                status = AcceptCode.ACCEPT_STATUS_REFUSE;
                break;
        }

        if(status.equals("")){
            return "redirect:/admin/teacherApplyFileDetail/" + teacherHistoryNo;
        }

        boolean result = teacherService.upgradeGrade(teacherHistoryNo, status, reason);

        if(result) {
            return "redirect:/admin/teacherApplyFileDetail/" + teacherHistoryNo;
        } else {
            return "redirect:/admin/teacherApplyDetail/" + mno;
        }
    }

    // Teacher

    @GetMapping("/upgradeTeacher")
    public String upgradeTeacher(Model model){
        return "teacher/upgrade";
    }

    /*@GetMapping("/stateTeacher")
    public String stateTeacher(@Param("type") int type, @Param("mno") Long teacherNo) throws Exception {
        teacherService.changeActive(teacherNo, type);
        return "redirect:/";
    }*/

}