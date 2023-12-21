package com.edutech.team26.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class HomeController {

    @GetMapping("/")
    public String home(Model model) throws Exception {
        return "index";
    }

    @GetMapping("/howToUse")
    public String howToUse(Model model) throws Exception {
        return "howToUse";
    }

    @GetMapping("/privacyPolicy")
    public String privacyPolicy(Model model) throws Exception {
        return "privacyPolicy";
    }

    @GetMapping("/layout")
    public String layout(Model model) throws Exception {
        return "contentLayout";
    }

    @GetMapping("/layout2")
    public String layout2(Model model) throws Exception {
        return "contentLayout2";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT', 'ADMIN')")
    @GetMapping("/lmsLayout")
    public String lmsLayout(Model model) throws Exception {
        return "testLayout/layoutLmsContent";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT', 'ADMIN')")
    @GetMapping("/lmsLayoutLec")
    public String lmsLayoutLec(Model model) throws Exception {
        return "testLayout/layoutLecLmsContent";
    }

}