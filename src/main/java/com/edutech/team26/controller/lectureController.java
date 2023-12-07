package com.edutech.team26.controller;

import com.edutech.team26.biz.CategoryService;
import com.edutech.team26.biz.LectureService;
import com.edutech.team26.dto.LectureDTO;
import com.edutech.team26.model.LectureParam;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/lecture")
public class lectureController extends lecBaseController{

    private final LectureService lectureService;
    private final CategoryService categoryService;
    private final ModelMapper mapper;

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

//
//    // add form
//    @GetMapping("/save")
//    public String addForm(Model model) {
//        model.addAttribute("category", categoryService.list());
//        return "lecture/add";
//    }
//
//
//    @PostMapping(value = {"/save"})
//    public String saveSubmit(Model model,HttpServletRequest request, MultipartFile[] file,LectureDTO lectureDTO) throws IOException {
//        lectureService.addLecture(lectureDTO,file,request);
//        return "redirect:/lecture/list";
//    }
//
//
//
//    @GetMapping(value = {"/edit"})
//    public String add(Model model, HttpServletRequest request, LectureDTO lectureDTO){
//
//        model.addAttribute("category", categoryService.list());
//
//        boolean editMode = request.getRequestURI().contains("/edit");
//        LectureDTO detail = new LectureDTO();
//
//        if (editMode) {
//            long id = lectureDTO.getLecture_no();
//
//            LectureDTO existLecture = lectureService.getById(id);
//
//            if (existLecture == null) {
//                model.addAttribute("message", "강좌 정보가 존재하지 않습니다.");
//                return "common/error";
//            }
//            detail = existLecture;
//
//        }
//
//        model.addAttribute("editMode", editMode);
//        model.addAttribute("detail", detail);
//        return "lecture/addLecture";
//    }


//    private String[] getNewSaveFile(String baseLocalPath,
//                                    String baseUrlPath,
//                                    String originalFilename)
//    {
//
//        LocalDate now = LocalDate.now();
//
//        String[] dirs = {
//                String.format("%s/%d/", baseLocalPath, now.getYear()),
//                String.format("%s/%d/%02d/", baseLocalPath,
//                        now.getYear(), now.getMonthValue()),
//                String.format("%s/%d/%02d/%02d/", baseLocalPath,
//                        now.getYear(), now.getMonthValue(), now.getDayOfMonth())};
//
//        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath,
//                now.getYear(), now.getMonthValue(), now.getDayOfMonth());
//
//        for(String dir : dirs) {
//            File file = new File(dir);
//            if (!file.isDirectory()) {
//                file.mkdir();
//            }
//        }
//
//        String fileExtension = "";
//        if (originalFilename != null) {
//            int dotPos = originalFilename.lastIndexOf(".");
//            if (dotPos > -1) {
//                fileExtension = originalFilename.substring(dotPos + 1);
//            }
//        }
//
//        String uuid = UUID.randomUUID().toString().replace("-", "");
//        String newFilename = String.format("%s%s", dirs[2], uuid);
//        String newUrlFilename = String.format("%s%s", urlDir, uuid);
//        if (fileExtension.length() > 0) {
//            newFilename += "." + fileExtension;
//            newUrlFilename += "." + fileExtension;
//        }
//
//        String[] returnFilename = {newFilename, newUrlFilename};
//
//        return returnFilename;
//    }

//    @PostMapping(value = {"/add"})
//    public String addSubmit(Model model,HttpServletRequest request, MultipartFile[] file,LectureDTO lectureDTO) throws IOException {

//        String saveFilename = "";
//        String urlFilename = "";
//
//        if (file != null) {
//            String originalFilename = file.getOriginalFilename();
//            String baseLocalPath = "C:/files";
//            String baseUrlPath = "/files";
//
//            String urlPath = "/files/2022/07/16/8f85120b80c34460934a6d7b58407615.png";
//
//            String[] arrFilename = getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);
//
//            saveFilename = arrFilename[0];
//            urlFilename = arrFilename[1];
//
//            try {
//                File newFile = new File(saveFilename);
//                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
////        lectureInput.setFilename(saveFilename);
////        lectureInput.setUrlFilename(urlFilename);
//          lectureDTO.setLectureImg1(saveFilename);
//          lectureDTO.setFilePath(urlFilename);
//
//        boolean editMode = request.getRequestURI().contains("/edit");
//        LectureDTO detail = new LectureDTO();
//
//        if (editMode) {
//            long id = lectureDTO.getLecture_no();
//            LectureDTO existLecture = lectureService.getById(id);
//
//
//            if (existLecture == null) {
//                model.addAttribute("message", "강좌 정보가 존재하지 않습니다.");
//                return "common/error";
//            }
//            boolean result = lectureService.set(lectureDTO);
//        } else {
//            boolean result = lectureService.add(lectureDTO,file,request);
//        }


//        return "redirect:/lecture/list";
//    }

//    @PostMapping("/admin/lecture/delete.do")
//    public String del(Model model,
//                      HttpServletRequest request,
//                      LectureDTO lectureDTO)
//    {
//
//        boolean result = lectureService.del(lectureDTO.getIdList());
//
//        return "redirect:/admin/lecture/list.do";
//    }
    
}
