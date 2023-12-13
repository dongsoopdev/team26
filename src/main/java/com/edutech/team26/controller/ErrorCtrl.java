package com.edutech.team26.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorCtrl implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object status02 = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());
            model.addAttribute("statusCode", statusCode);
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/notFoundError";
            } else {
                return "error/error";
            }
        }
        return "error/error";
    }

}