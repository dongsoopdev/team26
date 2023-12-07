package com.edutech.team26.controller;

import com.edutech.team26.biz.CategoryService;
import com.edutech.team26.biz.LectureService;
import com.edutech.team26.biz.TeacherService;
import com.edutech.team26.domain.Member;
import com.edutech.team26.domain.Teacher;
import com.edutech.team26.dto.LectureDTO;
import com.edutech.team26.model.LectureParam;
import com.edutech.team26.model.VwTeacher;
import com.edutech.team26.repository.MemberRepository;
import com.edutech.team26.repository.TeacherRepository;
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
import java.util.ArrayList;
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
    private TeacherService teacherService;

    @Autowired
    private TeacherRepository teacherRepository;


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
    @GetMapping("/lectureSave")
    public String addForm(Model model) {

//        List<VwTeacher> vwTeachers = new ArrayList<>();


//        List<VwTeacher> teacherList = teacherService.findAll();
//        List<String> memberNames = new ArrayList<>();
//        for (Teacher teacher : teacherList) {
//            Long mno = teacher.getMno();
//            Member member = memberRepository.findByMno(mno);
//            if (member != null) {
//                String memberName = member.getUserName();
//                memberNames.add(memberName);
//            } else {
//                memberNames.add("Unknown Member");
//            }
//        }
//        System.out.println("강사리스트 : " + teacherList);
//        model.addAttribute("teacherList", teacherList ); //선생님 이름 담은 객체
        model.addAttribute("category", categoryService.list());
        return "admin/addLecture";
    }


    @PostMapping(value = {"/save"})
    public String saveSubmit(Model model, HttpServletRequest request, MultipartFile[] file, LectureDTO lectureDTO) throws IOException {
        lectureService.addLecture(lectureDTO, file, request);
        return "redirect:/admin/lectureList";
    }

    //강의 수정하기
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
    
    
    //강의 삭제하기


}