package com.edutech.team26.biz;


import com.edutech.team26.constant.MemberRole;
import com.edutech.team26.domain.*;
import com.edutech.team26.dto.LectureDTO;
import com.edutech.team26.dto.MemberSecurityDTO;
import com.edutech.team26.dto.StudentDTO;

import com.edutech.team26.mapper.LectureMapper;
import com.edutech.team26.model.LectureParam;
import com.edutech.team26.model.ServiceResult;
import com.edutech.team26.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
@Log4j2
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final StudentRepository studentRepository;
    private final MemberRepository memberRepository;
    private final LectureMapper lectureMapper;
    private final ModelMapper modelMapper;
    private final TeacherRepository teacherRepository;
    private final VwCourseRepository vwCourseRepository;
    private final VwLectureRepository vwLectureRepository;


    private LocalDate getLocalDate(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(value, formatter);
        } catch (Exception e) {

        }

        return null;
    }

    @Override
    public List<VwLecture> findAll() {



        return vwLectureRepository.findAll();
    }

    //비즈니스 로직 추가시 쓸 메서드
    @Override
    public List<VwLecture> vwFindAll() {
        List<VwLecture> lectureList = vwLectureRepository.findAll();





        return lectureList;
    }

//    @Override
//    public List<TeacherVO> vwFindAll() {
//        List<VwCourse> VwCourses = VwCourseRepository.findAll();
//
//        // 수정된 부분: List<VwCourse>를 List<TeacherVO>로 매핑
//        List<TeacherVO> teacherVOList = VwCourses.stream()
//                .map(VwCourse -> modelMapper.map(VwCourse, TeacherVO.class))
//                .collect(Collectors.toList());
//
//        return teacherVOList;
//    }



//    // 강의 상태 변경
//    public void updateLectureAct(Lecture lecture) {
//        // Get the current date
//        LocalDate currentDate = LocalDate.now();
//
//        // Parse the start and end study dates
//        LocalDate startStudyLocalDate = LocalDate.parse(lecture.getStartStudyDate(), DateTimeFormatter.ISO_LOCAL_DATE);
//        LocalDate endStudyLocalDate = LocalDate.parse(lecture.getEndStudyDate(), DateTimeFormatter.ISO_LOCAL_DATE);
//
//        // Create a new Lecture entity and set lectureAct based on the conditions
//        if (startStudyLocalDate.isAfter(currentDate)) {
//            lecture.setLectureAct(1); // 강의예정
//        } else if (startStudyLocalDate.isBefore(currentDate) && endStudyLocalDate.isAfter(currentDate)) {
//            lecture.setLectureAct(2); // 강의진행중
//        } else if (endStudyLocalDate.isBefore(currentDate)) {
//            lecture.setLectureAct(3); // 강의종료
//        }
//
//        // Optionally, you might want to persist the changes to the database
//        // Uncomment the following line if you are using Spring Data JPA
//        // lectureRepository.save(lecture);
//    }



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

        //lectureDTO.setLectureAct(1);

        Lecture lecture = modelMapper.map(lectureDTO, Lecture.class);

        lectureRepository.save(lecture);

    }

    @Override
    public void updateLecture(LectureDTO lectureDTO, Long lecture_no, MultipartFile[] imgFiles) throws IOException {
        Lecture update = lectureRepository.getById(lecture_no);
        update.getLecture_no();
        update.setStartEnrolmentDate(lectureDTO.getStartEnrolmentDate());
        update.setEndEnrolmentDate(lectureDTO.getEndEnrolmentDate());
        update.setStartStudyDate(lectureDTO.getStartStudyDate());
        update.setEndStudyDate(lectureDTO.getEndEnrolmentDate());
        update.setLectureAct(lectureDTO.getLectureAct());
        update.setLectureContent(lectureDTO.getLectureContent());
        update.setLectureCurnum(lectureDTO.getLectureCurnum());
        update.setLectureMaxnum(lectureDTO.getLectureMaxnum());
        update.setLectureMinnum(lectureDTO.getLectureMinnum());
        update.setLectureName(lectureDTO.getLectureName());
        update.setZoomUrl(lectureDTO.getZoomUrl());
        update.setTeacher(teacherRepository.getById(lectureDTO.getTeacher_no()));


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

            update.setFilePath(uploadPath);

            // 각 이미지에 대한 처리 (imgsrc1, imgsrc2,vedio)
            switch (i + 1) {
                case 1:
                    update.setLectureImg1(imgName);
                    break;
                case 2:
                    update.setLectureImg2(imgName);
                    break;
                case 3:
                    update.setLectureVedio(imgName);
                    break;
                // 추가적인 이미지 필요에 따라 계속해서 확장 가능

            }
        }

        //lectureDTO.setLectureAct(1);
       // Lecture lecture = modelMapper.map(update, Lecture.class);

        lectureRepository.save(update);

    }

/*    @Override
    public boolean set(LectureDTO lectureDTO) {

        //LocalDate saleEndAt = getLocalDate(lectureDTO.getSaleEndAtText());

        Optional<Lecture> optionalLecture = lectureRepository.findById(lectureDTO.getLecture_no());
        if (!optionalLecture.isPresent()) {
            return false;
        }

        Lecture lecture = modelMapper.map(lectureDTO, Lecture.class);


        return true;
    }*/

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
    public LectureDTO getById(long id) {

        return lectureRepository.findById(id).map(LectureDTO::of).orElse(null);
    }

    @Override
    public void deleteLecture(long lectureNo) {
        Lecture lecture = lectureRepository.getById(lectureNo);
        lecture.updateLectureAct(5);
        lectureRepository.save(lecture);

    }

    @Override
    public void deleteCancleLecture(long lectureNo) {
        Lecture lecture = lectureRepository.getById(lectureNo);
        lecture.updateLectureAct(1);
        lecture.setLectureCurnum(0); // 수강신청인원 초기화
        lectureRepository.save(lecture);
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


    // 수강 신청
    @Override
    public ServiceResult apply(StudentDTO studentDTO) {
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        Optional<Member> m = memberRepository.findById(member.getMno());
//        if(m.isEmpty()){
//            return false;
//        }
//
//        Optional<Student> checkStudent = studentRepository.findByMnoAndLectureNo(member.getMno(), lectureNo);
//        if(checkStudent.isPresent()) {
//            return false;
//        }


        ServiceResult result = new ServiceResult();

        Optional<Lecture> optionalLecture = lectureRepository.findById(studentDTO.getLectureNo());
        if (!optionalLecture.isPresent()) {
            result.setResult(false);
            result.setMessage("강좌 정보가 존재하지 않습니다.");
            return result;
        }

        Lecture lecture = optionalLecture.get();

/*        String[] statusList = {Student.STATUS_REQ, Student.STATUS_COMPLETE};
        long count = studentRepository.countBylectureNoAndStudentNoAndStatusIn(
                lecture.getLecture_no(), studentDTO.getMno(), Arrays.asList(statusList));

        if (count > 0) {
            result.setResult(false);
            result.setMessage("이미 신청한 강좌 정보가 존재합니다.");
            return result;
        }*/

        // 수강 정보 인서트
        Student takeLecture = Student.builder()
                .lectureNo(lecture.getLecture_no())
                .mno(studentDTO.getMno())
                .entranceYn(false)
                .status(Student.STATUS_REQ)
                .regDate(LocalDateTime.now())

                .build();
        studentRepository.save(takeLecture);


        Lecture lec = lectureRepository.getById(studentDTO.getLectureNo());

        // 수강인원 up
        lec.setLectureCurnum(lec.getLectureCurnum() + 1);
        lectureRepository.save(lec);


/*        // 수강인원 up
        Lecture updatedLecture = Lecture.builder()  // Lecture 엔터티를 새로운 빌더로 생성
                .lecture_no(lec.getLecture_no())  // 현재 강의번호 설정
                .lectureCurnum(lec.getLectureCurnum() + 1)  // 현재 수강인원에서 1 증가
                .build();
        lectureRepository.save(updatedLecture);  // 변경된 Lecture 엔터티 저장*/

        System.out.println("=============인원테스트==============="+lec.getLectureCurnum());

        Member memberInfo = memberRepository.findByMno(member.getMno());

        Member memberUpgrade = modelMapper.map(memberInfo, Member.class);
        memberUpgrade.updateRole(MemberRole.STUDENT);
        memberRepository.save(memberUpgrade);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
        updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);


        result.setResult(true);
        result.setMessage("");

        return result;
    }


    // 수강신청
//    @Override
//    public ServiceResult apply(StudentDTO studentDTO) {
//
//
//        ServiceResult result = new ServiceResult();
//
//        Optional<Lecture> optionalLecture = lectureRepository.findById(studentDTO.getLectureNo());
//        if (!optionalLecture.isPresent()) {
//            result.setResult(false);
//            result.setMessage("강좌 정보가 존재하지 않습니다.");
//            return result;
//        }
//
//        Lecture lecture = optionalLecture.get();
//
//        String[] statusList = {Student.STATUS_REQ, Student.STATUS_COMPLETE};
//        long count = studentRepository.countByLectureNoAndStudentNoAndStatusIn(
//                lecture.getLecture_no(), studentDTO.getMno(), Arrays.asList(statusList));
//
//        if (count > 0) {
//            result.setResult(false);
//            result.setMessage("이미 신청한 강좌 정보가 존재합니다.");
//            return result;
//        }
//
//        Student takeLecture = Student.builder()
//                .lectureNo(lecture.getLecture_no())
//                .mno(studentDTO.getMno())
//                .entranceYn(false)
//                .status(Student.STATUS_REQ)
//                .regDate(LocalDateTime.now())
//
//                .build();
//
//        studentRepository.save(takeLecture);
//
//        // user -> student로 권한변경
//        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Member memberInfo = memberRepository.findByMno(member.getMno());
//        Member memberUpgrade = modelMapper.map(memberInfo, Member.class);
//        memberUpgrade.updateRole(MemberRole.STUDENT);
//        memberRepository.save(memberUpgrade);
//
//
//        result.setResult(true);
//        result.setMessage("");
//
//        return result;
//    }
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