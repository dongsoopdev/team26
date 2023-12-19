package com.edutech.team26.controller;

import com.edutech.team26.biz.CategoryService;
import com.edutech.team26.biz.LectureService;
import com.edutech.team26.biz.StudentService;
import com.edutech.team26.domain.Lecture;
import com.edutech.team26.domain.Student;
import com.edutech.team26.domain.VwCourse;
import com.edutech.team26.domain.VwLecture;
import com.edutech.team26.dto.LectureDTO;
import com.edutech.team26.dto.MemberSecurityDTO;
import com.edutech.team26.dto.StudentDTO;
import com.edutech.team26.model.LectureParam;
import com.edutech.team26.model.ResponseResult;
import com.edutech.team26.model.ServiceResult;
import com.edutech.team26.repository.LectureRepository;
import com.edutech.team26.repository.StudentRepository;
import com.edutech.team26.repository.VwCourseRepository;
import com.edutech.team26.repository.VwLectureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/lecture")
public class LectureController extends lecBaseController {

    private final LectureService lectureService;
    private final CategoryService categoryService;
    private final StudentService studentService;
    private final ModelMapper mapper;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;
    private final VwLectureRepository vwLectureRepository;
    private final VwCourseRepository vwCourseRepository;

    @GetMapping("lectureList")
    public String list(Model model, LectureParam lectureParam) {

//        lectureParam.init();
//        List<lectureDTO> lectureList = lectureService.list(lectureParam);

//        long totalCount = 0;
//        if (!CollectionUtils.isEmpty(lectureList)) {
//            totalCount = lectureList.get(0).getTotalCount();
//        }
//
//        String queryString = lectureParam.getQueryString();
//        String pageHtml = getPaperHtml(totalCount,
//                lectureParam.getPageSize(),
//                lectureParam.getPageIndex(),
//                queryString);
//        model.addAttribute("totalCount", totalCount);
//        model.addAttribute("pager", pageHtml);

        List<VwLecture> lectureList = vwLectureRepository.findAllByOrderByLecRegDateDesc(); //최신등록순
        //List<VwLecture> lectureList = vwLectureRepository.findAll();
        model.addAttribute("lectureList", lectureList);

        return "lecture/lectureList";
    }




    // 강의 상세보기
    @GetMapping("/getlecture/{lecture_no}")
    public String getlecture(@PathVariable("lecture_no") long lecture_no, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        //강의 정보 가져오기
        LectureDTO lecture = lectureService.getById(lecture_no);
        System.out.println(lecture);
        VwLecture vwLecture = vwLectureRepository.getBylectureNo(lecture_no);

        model.addAttribute("vwLecture",vwLecture);
        model.addAttribute("lecture", lecture);

        return "lecture/lectureDetail";
    }





//    @PostMapping("/api/course/req.api")
//    public ResponseEntity<?> courseReq(Model model,
//                                       @RequestBody StudentDTO studentDTO,
//                                       Principal principal) throws Exception {
//
//        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        boolean apply = studentService.applyStudent(member.getMno(), studentDTO.getlectureNo());
//
//        studentDTO.setMno(member.getMno());
//
//
//        if (!apply) {
//            ResponseResult responseResult = new ResponseResult(false, "등록 실패");
//            return ResponseEntity.ok().body(responseResult);
//        }
//
//        ServiceResult result = lectureService.apply(studentDTO);
//        ResponseResult responseResult = new ResponseResult(true);
//        return ResponseEntity.ok().body(responseResult);
//    }


    @PostMapping("/api/course/req.api")
    public ResponseEntity<?> courseReq(Model model,
                                       @RequestBody StudentDTO studentDTO,
                                       Principal principal) {

        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        studentDTO.setMno(member.getMno());
        ServiceResult result = lectureService.apply(studentDTO);


        if (!result.isResult()) {

            ResponseResult responseResult = new ResponseResult(false, result.getMessage());
            return ResponseEntity.ok().body(responseResult);
        }

        ResponseResult responseResult = new ResponseResult(true);
        return ResponseEntity.ok().body(responseResult);
    }


    @GetMapping("/api/checkEnrollment.api")
    public ResponseEntity<Map<String, Object>> checkEnrollment(@RequestParam long lectureNo) {
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       // Optional<Student> student = studentRepository.findByMnoAndLectureNo(member.getMno(),lectureNo);

        Map<String, Object> response = new HashMap<>();
        boolean isEnrolled = vwCourseRepository.countByLectureNoAndMnoAndStudentStatus(lectureNo,member.getMno(), "REQ") > 0; // yourStudentId를 실제 사용하는 ID로 변경해야 합니다.

        Map<String, Object> header = new HashMap<>();
        if (isEnrolled) {
            header.put("result", true);
            header.put("message", "이미 수강 중인 강의입니다.");
        } else {
            header.put("result", false);
            header.put("message", "수강 가능한 강의입니다.");
        }

        response.put("header", header);

        return ResponseEntity.ok(response);
    }





}
