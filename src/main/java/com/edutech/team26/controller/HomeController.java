package com.edutech.team26.controller;

import lombok.extern.log4j.Log4j2;
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

    @GetMapping("/layout")
    public String layout(Model model) throws Exception {
        return "contentLayout";
    }

}