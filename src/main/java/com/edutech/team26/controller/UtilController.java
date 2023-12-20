package com.edutech.team26.controller;

import com.edutech.team26.biz.FilesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Log4j2
@Controller
@RequiredArgsConstructor
public class UtilController {

    private final FilesService filesService;

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT', 'USER')")
    @GetMapping("/util/fileDownload/{fileNo}")
    public String fileDownload(@PathVariable(required = false) Long fileNo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String urlPath = filesService.filesDownload(fileNo, request, response);
        return "redirect:" + urlPath;
    }

}