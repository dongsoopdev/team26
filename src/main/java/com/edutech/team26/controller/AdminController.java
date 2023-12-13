package com.edutech.team26.controller;

import com.edutech.team26.biz.CategoryService;
import com.edutech.team26.biz.LectureService;
import com.edutech.team26.biz.TeacherService;
import com.edutech.team26.domain.VwCourse;
import com.edutech.team26.domain.VwLecture;
import com.edutech.team26.domain.VwTeacher;
import com.edutech.team26.dto.LectureDTO;
import com.edutech.team26.model.LectureParam;
import com.edutech.team26.repository.TeacherRepository;
import com.edutech.team26.repository.VwCourseRepository;
import com.edutech.team26.repository.VwLectureRepository;
import com.edutech.team26.repository.VwTeacherRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private ModelMapper mapper;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private VwTeacherRepository vwTeacherRepository;

    @Autowired
    private VwCourseRepository vwCourseRepository;
    @Autowired
    private VwLectureRepository vwLectureRepository;



    /*@PreAuthorize("hasRole('ADMIN')")*/
    @GetMapping("/home")
    public String adminHome(Model model) {
        return "admin/home";
    }


    // ===========================================   [강의 관련 시작]  ============================================================
    
    // 개설 강좌 전체 리스트
    @GetMapping("/lectureList")
    public String lecturelist(Model model, LectureParam lectureParam) {

//        lectureParam.init();
//        List<lectureDTO> lectureList = lectureService.list(lectureParam);

//        long totalCount = 0;
//        if (!CollectionUtils.isEmpty(lectureList)) {
//            totalCount = lectureList.get(0).getTotalCount();
//        }
//
//        String queryString = lectureParam.getQueryString();
//        String pageHtml  = getPaperHtml(totalCount,
//                lectureParam.getPageSize(),
//                lectureParam.getPageIndex(),
//                queryString);
//        model.addAttribute("totalCount", totalCount);
//        model.addAttribute("pager", pageHtml);


        List<VwLecture> lectureList = vwLectureRepository.findAll();

        System.out.println("전체 강좌" + lectureList.toString());
        model.addAttribute("lectureList", lectureList);

        return "admin/lecture/lectureList";
    }

    // 선택한 강의 상세보기
    @GetMapping("/getlecture/{lecture_no}")
    public String getProduct(@PathVariable("lecture_no") long lecture_no, Model model) {

        //상품 정보
        LectureDTO lectureDTO = lectureService.getById(lecture_no);
        System.out.println(lectureDTO);


        model.addAttribute("lecture", lectureDTO);
        return "admin/lecture/getlecture";
    }




    // add form
    @GetMapping("/lectureSave")
    public String addForm(Model model) {

//        List<Vwlecture> Vwlectures = new ArrayList<>();


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
        /*List<Teacher> teacherList = teacherRepository.findAll();
        System.out.println("강사리스트 : " + teacherList);*/

        List<VwTeacher> teacherList =  vwTeacherRepository.findAll();
        System.out.println("강사리스트 : " + teacherList);

        model.addAttribute("teacherList", teacherList ); //선생님 이름 담은 객체
        model.addAttribute("category", categoryService.list());
        return "admin/lecture/addlecture";
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

   /*     model.addAttribute("category", categoryService.list());

        boolean editMode = request.getRequestURI().contains("/edit");
        lectureDTO detail = new lectureDTO();

        if (editMode) {
            long id = lectureDTO.getLecture_no();

            lectureDTO existlecture = lectureService.getById(id);

            if (existlecture == null) {
                model.addAttribute("message", "강좌 정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = existlecture;

        }
        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);*/
        return "lecture/addlecture";
    }
    
    
    //강의 삭제하기



    // ===========================================   [강의 관련 끝]  ============================================================





    // ===========================================   [수강생 관련 시작]  ============================================================

    @GetMapping("/courseList")
    public String courseList(Model model, LectureParam lectureParam) {

        List<VwCourse> courseList = vwCourseRepository.findAll();

        System.out.println("전체 강좌" + courseList.toString());
        model.addAttribute("courseList", courseList);

        return "admin/lecture/courseList";
    }

    // ===========================================   [수강생 관련 끝]  ============================================================

}