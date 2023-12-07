package com.edutech.team26.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/student")
public class StudentController {
    @GetMapping("/home")
    public String studentHome(Model model) {
        log.info("---------------------- STUDENT ----------------------");
        return "student/home";
    }
}
