package com.edutech.team26.controller;

import com.edutech.team26.biz.MemberService;
import com.edutech.team26.biz.StudentService;
import com.edutech.team26.biz.TeacherService;
import com.edutech.team26.dto.*;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/member/pw-auth/{id}")
    public String resetPwAuth(@PathVariable(required = false) String id, Model model){
        boolean result = memberService.emailAuth(id);
        model.addAttribute("result", result);

        return "redirect:/myPage";
    }

    //@PreAuthorize("hasRole('USER')") // 권한 한개
    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT', 'USER')")
    @GetMapping("/myPage")
    public String myPage(Model model) {
        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberDTO memberDTO = memberService.getMemberInfo(memberSecurityDTO.getMno());
        model.addAttribute("memberDTO", memberDTO);
        return "member/myPage";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT', 'USER')")
    @GetMapping("/myPage/modifyInfo")
    public String myPageInfo(Model model) {
        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberDTO memberDTO = memberService.getMemberInfo(memberSecurityDTO.getMno());
        model.addAttribute("memberDTO", memberDTO);
        return "member/myPageInfo";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT', 'USER')")
    @PostMapping("/myPage/modifyInfo")
    public String myPageInfoPOST(@Valid MemberDTO memberDTO, BindingResult bindingResult, Model model) throws Exception {
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
        return "redirect:/myPage";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT', 'USER')")
    @GetMapping("/myPage/resetPw")
    public String myPagePw(Model model) {
        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberDTO memberDTO = memberService.getMemberInfo(memberSecurityDTO.getMno());
        MemberPwDTO memberPwDTO = new MemberPwDTO();
        memberPwDTO.setPassword(memberDTO.getPassword());
        model.addAttribute("memberPwDTO", memberPwDTO);
        return "member/myPagePw";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT', 'USER')")
    @PostMapping("/myPage/resetPw")
    public String myPagePwPro(@Valid MemberPwDTO memberPwDTO, BindingResult bindingResult, Model model) {
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

        return "redirect:/myPage";
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
        return "member/teacherApply";
    }

    @PostMapping("/myPage/teacherApply")
    public String teacherApplyPro(HttpServletRequest request, List<MultipartFile> uploadFiles) throws Exception {
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        teacherService.applyGrade(member.getMno(), uploadFiles, request);
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

    // Teacher

    @GetMapping("/upgradeTeacher")
    public String upgradeTeacher(Model model){
        return "teacher/upgrade";
    }

    @GetMapping("/stateTeacher")
    public String stateTeacher(@Param("type") int type, @Param("mno") Long teacherNo) throws Exception {
        teacherService.changeActive(teacherNo, type);
        return "redirect:/";
    }

}