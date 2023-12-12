package com.edutech.team26.controller;

import com.edutech.team26.biz.CustomUserDetailsService;
import com.edutech.team26.biz.MemberService;
import com.edutech.team26.biz.StudentService;
import com.edutech.team26.biz.TeacherService;
import com.edutech.team26.dto.MemberJoinDTO;
import com.edutech.team26.dto.MemberSecurityDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
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

import java.lang.reflect.Field;
import java.security.Principal;

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
        MemberJoinDTO member = new MemberJoinDTO();
        model.addAttribute("member", member);
        return "member/join";
    }

    @GetMapping("/joinFinish")
    public String joinFinish(Model model){
        //MemberJoinDTO member = new MemberJoinDTO();
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

        /*boolean result = false;
        try {
            result = memberService.join(memberJoinDTO);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "email");
        }

        redirectAttributes.addFlashAttribute("result", result);
        return "member/joinComplete"; //회원 가입 후 로그인*/
        /*boolean result = memberService.join(memberJoinDTO);
        if(!result) {
            model.addAttribute("errorMessage", "이메일 중복 검사를 다시 해주시기 바랍니다.");
            return "member/join";
        } else {
            return "member/joinComplete";
        }*/

        if(bindingResult.hasErrors()){
            return "member/join";
        }

        try {
            memberService.join(memberJoinDTO);
        } catch (IllegalStateException e){

            model.addAttribute("errorMessage", "이메일 중복 검사를 진행해 주시기 바랍니다.");

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

    //@PreAuthorize("hasRole('USER')") // 권한 한개
    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT', 'USER')") // 권한 여러개
    @GetMapping("/mypage")
    public String myPage(Model model) throws NoSuchFieldException {

        // member 토큰 정보 가져오기
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //String email = principal.getName();
        //MemberJoinDTO memberDto = memberService.myinfo(email);
        //log.info(memberDto);
        //model.addAttribute("memberDto",memberDto);
        return "member/mypage";
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

    @PostMapping("/upgradeTeacher")
    public String upgradeTeacherPro(HttpServletRequest request, Model model, MultipartFile uploadFile) throws Exception {
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        teacherService.updateGrade(member.getMno(), uploadFile, request);
        return "redirect:/";
    }

    @GetMapping("/stateTeacher")
    public String stateTeacher(@Param("type") int type, @Param("mno") Long teacherNo) throws Exception {
        teacherService.changeActive(teacherNo, type);
        return "redirect:/";
    }

}