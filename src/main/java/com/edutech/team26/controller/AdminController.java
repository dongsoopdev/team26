package com.edutech.team26.controller;

import com.edutech.team26.biz.CategoryService;
import com.edutech.team26.biz.LectureService;
import com.edutech.team26.dto.LectureDTO;
import com.edutech.team26.model.LectureParam;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController extends lecBaseController{

    @Autowired
    private LectureService lectureService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;



    /*@PreAuthorize("hasRole('ADMIN')")*/
    @GetMapping("/home")
    public String adminHome(Model model) {
        log.info("---------------------- ADMIN ----------------------");
        return "admin/home";
    }


    @GetMapping("lectureList")
    public String list(Model model, LectureParam lectureParam) {

//        lectureParam.init();
//        List<LectureDTO> lectureList = lectureService.list(lectureParam);


        List<LectureDTO> lectureList = lectureService.findAll();

        long totalCount = 0;
        if (!CollectionUtils.isEmpty(lectureList)) {
            totalCount = lectureList.get(0).getTotalCount();
        }

        String queryString = lectureParam.getQueryString();
        String pageHtml  = getPaperHtml(totalCount,
                lectureParam.getPageSize(),
                lectureParam.getPageIndex(),
                queryString);

        model.addAttribute("lectureList", lectureList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pageHtml);

        return "lecture/lectureList";
    }


    // add form
    @GetMapping("/save")
    public String addForm(Model model) {
        model.addAttribute("category", categoryService.list());
        return "admin/addLecture";
    }


    @PostMapping(value = {"/save"})
    public String saveSubmit(Model model, HttpServletRequest request, MultipartFile[] file, LectureDTO lectureDTO) throws IOException {
        lectureService.addLecture(lectureDTO, file, request);
        return "redirect:/admin/lectureList";
    }


    @GetMapping(value = {"/edit"})
    public String add(Model model, HttpServletRequest request, LectureDTO lectureDTO) {

        model.addAttribute("category", categoryService.list());

        boolean editMode = request.getRequestURI().contains("/edit");
        LectureDTO detail = new LectureDTO();

        if (editMode) {
            long id = lectureDTO.getLecture_no();

            LectureDTO existLecture = lectureService.getById(id);

            if (existLecture == null) {
                model.addAttribute("message", "강좌 정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = existLecture;

        }
        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);
        return "lecture/addLecture";
    }
}