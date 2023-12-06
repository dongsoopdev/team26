package com.edutech.team26.controller;

import com.edutech.team26.biz.CustomUserDetailsService;
import com.edutech.team26.biz.MemberService;
import com.edutech.team26.dto.MemberJoinDTO;
import com.edutech.team26.dto.MemberSecurityDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Field;
import java.security.Principal;

@Log4j2
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join_term")
    public String joinTerm(Model model){
        log.info("-------------------- joinTerm --------------------");
        return "member/joinTerm";
    }

    @GetMapping("/join")
    public String joinUser(Model model){
        MemberJoinDTO member = new MemberJoinDTO();
        model.addAttribute("member", member);
        return "member/join";
    }

    @PostMapping("/join")
    public String joinPOST(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes){
        log.info("-------------------- join post --------------------");
        log.info(memberJoinDTO);
        boolean result = false;
        try {
            result = memberService.join(memberJoinDTO);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "email");
        }

        redirectAttributes.addFlashAttribute("result", result);
        return "member/joinComplete"; //회원 가입 후 로그인
    }

    @GetMapping("/login")
    public String loginGet(){
        log.info("-------------------- login GET --------------------");
        return "member/login";
    }

    @GetMapping("/member/email-auth/{id}")
    public String emailAuth(@PathVariable(required = false) String id, Model model){
        log.info("-------------------- emailAuthPage --------------------");
        log.info(id);

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

        log.info("-------------------- MyInfo --------------------");
        log.info(">>>>> " + member.getMno());

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

}