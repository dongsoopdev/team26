package com.edutech.team26.controller;

import com.edutech.team26.biz.CategoryService;
import com.edutech.team26.biz.LectureService;
import com.edutech.team26.biz.TeacherService;
import com.edutech.team26.dto.LectureDTO;
import com.edutech.team26.model.LectureParam;
import com.edutech.team26.repository.TeacherRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private TeacherService teacherService;

    @Autowired
    private TeacherRepository teacherRepository;


    @Autowired
    private ModelMapper mapper;



    /*@PreAuthorize("hasRole('ADMIN')")*/
    @GetMapping("/home")
    public String adminHome(Model model) {
        return "admin/home";
    }


    
    
    
    // ===========================================   [강의 관련 시작]  ============================================================
    
    // 강의 전체 리스트
    @GetMapping("/lectureList")
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

        return "admin/lecture/lectureList";
    }

    // 선택한 강의 상세보기
    @GetMapping("/getLecture/{lecture_no}")
    public String getProduct(@PathVariable("lecture_no") long lecture_no, Model model) {

        //상품 정보
        LectureDTO lectureDTO = lectureService.getById(lecture_no);
        System.out.println(lectureDTO);


        model.addAttribute("lecture", lectureDTO);
        return "admin/lecture/getLecture";
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
        return "admin/lecture/addLecture";
    }


    //강의 등록하기
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








    // ===========================================   [강의 관련 끝]  ============================================================


}