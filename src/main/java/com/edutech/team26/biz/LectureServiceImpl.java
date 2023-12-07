package com.edutech.team26.biz;


import com.edutech.team26.domain.Lecture;
import com.edutech.team26.domain.Student;
import com.edutech.team26.dto.LectureDTO;
import com.edutech.team26.dto.StudentDTO;
import com.edutech.team26.mapper.LectureMapper;
import com.edutech.team26.model.LectureParam;
import com.edutech.team26.model.ServiceResult;
import com.edutech.team26.repository.LectureRepository;
import com.edutech.team26.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final StudentRepository studentRepository;
    private final LectureMapper lectureMapper;


    private LocalDate getLocalDate(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(value, formatter);
        } catch (Exception e) {

        }

        return null;
    }

    @Override
    public void addLecture(LectureDTO lectureDTO, MultipartFile[] imgFiles, HttpServletRequest request) throws IOException {
        String uploadPath = "C:/upload/";
        for (int i = 0; i < imgFiles.length; i++) {
            MultipartFile imgFile = imgFiles[i];
            String oriImgName = imgFile.getOriginalFilename();
            String imgName = "";

            // UUID 를 이용하여 파일명 새로 생성
            UUID uuid = UUID.randomUUID();
            String savedFileName = uuid + "_" + oriImgName;


            imgName = savedFileName;

            File saveFile = new File(uploadPath, imgName);
            imgFile.transferTo(saveFile);

            lectureDTO.setFilePath(uploadPath);

            // 각 이미지에 대한 처리 (imgsrc1, imgsrc2,vedio)
            switch (i + 1) {
                case 1:
                    lectureDTO.setLectureImg1(imgName);
                    break;
                case 2:
                    lectureDTO.setLectureImg2(imgName);
                    break;
                case 3:
                    lectureDTO.setLectureVedio(imgName);
                    break;
                // 추가적인 이미지 필요에 따라 계속해서 확장 가능

            }
        }

        Lecture lecture = Lecture.builder()
                .lecture_no(lectureDTO.getLecture_no())
                //.teacher_no(lectureDTO.getTeacher_no())
                .lectureName(lectureDTO.getLectureName())
                .lectureContent(lectureDTO.getLectureContent())
                .lectureImg1(lectureDTO.getLectureImg1())
                .lectureImg2(lectureDTO.getLectureImg2())
                .lectureVedio(lectureDTO.getLectureVedio())
                .filePath(lectureDTO.getFilePath())
                .zoomUrl(lectureDTO.getZoomUrl())
                .lectureCurnum(lectureDTO.getLectureCurnum())
                .lectureMinnum(lectureDTO.getLectureMinnum())
                .lectureMaxnum(lectureDTO.getLectureMaxnum())
                .startEnrolmentDate(lectureDTO.getStartEnrolmentDate())
                .endEnrolmentDate(lectureDTO.getEndEnrolmentDate())
                .startStudyDate(lectureDTO.getStartStudyDate())
                .endStudyDate(lectureDTO.getEndStudyDate())
                .lectureAct(lectureDTO.getLectureAct())
                .build();
        lectureRepository.save(lecture);

    }

    @Override
    public boolean set(LectureDTO lectureDTO) {

        //LocalDate saleEndAt = getLocalDate(lectureDTO.getSaleEndAtText());

        Optional<Lecture> optionalLecture = lectureRepository.findById(lectureDTO.getLecture_no());
        if (!optionalLecture.isPresent()) {
            return false;
        }

        Lecture lecture = optionalLecture.get();
        lecture.setLectureName(lectureDTO.getLectureName());
        lecture.setLectureContent(lectureDTO.getLectureContent());
        lecture.setLectureImg1(lectureDTO.getLectureImg1());
        lecture.setLectureImg2(lectureDTO.getLectureImg2());
        lecture.setLectureVedio(lectureDTO.getLectureVedio());
        lecture.setFilePath(lectureDTO.getFilePath());
        lecture.setZoomUrl(lectureDTO.getZoomUrl());
        lecture.setLectureCurnum(lectureDTO.getLectureCurnum());
        lecture.setLectureMinnum(lectureDTO.getLectureMinnum());
        lecture.setLectureMaxnum(lectureDTO.getLectureMaxnum());
        lecture.setStartEnrolmentDate(lectureDTO.getStartEnrolmentDate());
        lecture.setEndEnrolmentDate(lectureDTO.getEndEnrolmentDate());
        lecture.setStartStudyDate(lectureDTO.getStartStudyDate());
        lecture.setEndStudyDate(lectureDTO.getEndStudyDate());
        lecture.setEndStudyDate(lectureDTO.getEndStudyDate());
        lecture.setLectureAct(lectureDTO.getLectureAct());


        lectureRepository.save(lecture);

        return true;
    }

    @Override
    public List<LectureDTO> list(LectureParam lectureParam) {

        long totalCount = lectureMapper.selectListCount(lectureParam);
        List<LectureDTO> list = lectureMapper.selectList(lectureParam);
        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (LectureDTO x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - lectureParam.getPageStart() - i);
                i++;
            }
        }
        return list;
    }

    @Override
    public List<LectureDTO> findAll() {
        List<Lecture> lectureList = lectureRepository.findAll();
        return LectureDTO.of(lectureList);
    }

    @Override
    public LectureDTO getById(long id) {

        return lectureRepository.findById(id).map(LectureDTO::of).orElse(null);
    }


//	@Override
//	public boolean del(String idList) {
//
//		if (idList != null && idList.length() > 0) {
//
//			String[] ids = idList.split(",");
//			for(String x: ids) {
//				long id = 0L;
//				try {
//					id = Long.parseLong(x);
//				} catch (Exception e) {
//
//				}
//
//				if (id > 0) {
//					lectureRepository.deleteById(id);
//				}
//			}
//		}
//		return true;
//	}

//	@Override
//	public List<LectureDTO> frontList(LectureParam lectureParam) {
//
//		if (lectureParam.getCategoryId() < 1) {
//			List<Lecture> lectureList = lectureRepository.findAll();
//			return LectureDTO.of(lectureList);
//		}
//
//		Optional<List<Lecture>> optionalLectureList =
//					lectureRepository.findByCategoryId(lectureParam.getCategoryId());
//		if (optionalLectureList.isPresent()) {
//			return LectureDTO.of(optionalLectureList.get());
//		}
//		return null;
//	}
//
//	@Override
//	public LectureDTO frontDetail(long id) {
//
//		Optional<Lecture> optionalLecture = lectureRepository.findById(id);
//		if (optionalLecture.isPresent()) {
//			return LectureDTO.of(optionalLecture.get());
//		}
//		return null;
//	}


    @Override
    public ServiceResult apply(StudentDTO studentDTO) {

        ServiceResult result = new ServiceResult();

        Optional<Lecture> optionalLecture = lectureRepository.findById(studentDTO.getLectureId());
        if (!optionalLecture.isPresent()) {
            result.setResult(false);
            result.setMessage("강좌 정보가 존재하지 않습니다.");
            return result;
        }

        Lecture lecture = optionalLecture.get();

        String[] statusList = {Student.STATUS_REQ, Student.STATUS_COMPLETE};
        long count = studentRepository.countByLectureIdAndUserIdAndStatusIn(
                lecture.getLecture_no(), studentDTO.getStudentNo(), Arrays.asList(statusList));

        if (count > 0) {
            result.setResult(false);
            result.setMessage("이미 신청한 강좌 정보가 존재합니다.");
            return result;
        }

        Student takeLecture = Student.builder()
                .studentNo(studentDTO.getStudentNo())
                .lectureNo(lecture.getLecture_no())
                .status(Student.STATUS_REQ)
                .regDate(LocalDateTime.now())

                .build();

        studentRepository.save(takeLecture);

        result.setResult(true);
        result.setMessage("");

        return result;
    }
//
//	@Override
//	public List<LectureDTO> listAll() {
//
//		List<Lecture> lectureList = lectureRepository.findAll();
//
//		return LectureDTO.of(lectureList);
//	}
//}
}