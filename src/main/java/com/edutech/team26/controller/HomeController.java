package com.edutech.team26.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/layout2")
    public String layout2(Model model) throws Exception {
        return "contentLayout2";
    }

}