package com.edutech.team26.biz;


import com.edutech.team26.constant.MemberRole;
import com.edutech.team26.domain.*;
import com.edutech.team26.dto.LectureDTO;
import com.edutech.team26.dto.MemberSecurityDTO;
import com.edutech.team26.dto.StudentDTO;

import com.edutech.team26.dto.TeacherVO;
import com.edutech.team26.mapper.LectureMapper;
import com.edutech.team26.model.LectureParam;
import com.edutech.team26.model.ServiceResult;
import com.edutech.team26.repository.LectureRepository;
import com.edutech.team26.repository.MemberRepository;
import com.edutech.team26.repository.StudentRepository;
import com.edutech.team26.repository.VwCourseRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final VwCourseRepository VwCourseRepository;



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

        lectureDTO.setLectureAct(1);

        Lecture lecture = modelMapper.map(lectureDTO, Lecture.class);


        lectureRepository.save(lecture);

    }

    @Override
    public boolean set(LectureDTO lectureDTO) {

        //LocalDate saleEndAt = getLocalDate(lectureDTO.getSaleEndAtText());

        Optional<Lecture> optionalLecture = lectureRepository.findById(lectureDTO.getLecture_no());
        if (!optionalLecture.isPresent()) {
            return false;
        }

        ///Lecture lecture = optionalLecture.get();
       /* lecture.setLectureName(lectureDTO.getLectureName());
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
        lecture.setLectureAct(lectureDTO.getLectureAct());*/

        Lecture lecture = modelMapper.map(lectureDTO, Lecture.class);


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
    public List<VwCourse> vwFindAll() {
       System.out.println("서비스에서 출력값: "+ VwCourseRepository.findAll());
        return VwCourseRepository.findAll();
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

        String[] statusList = {Student.STATUS_REQ, Student.STATUS_COMPLETE};
        long count = studentRepository.countByLectureNoAndStudentNoAndStatusIn(
                lecture.getLecture_no(), studentDTO.getMno(), Arrays.asList(statusList));

        if (count > 0) {
            result.setResult(false);
            result.setMessage("이미 신청한 강좌 정보가 존재합니다.");
            return result;
        }

        Student takeLecture = Student.builder()
                .lectureNo(lecture.getLecture_no())
                .mno(studentDTO.getMno())
                .entranceYn(false)
                .status(Student.STATUS_REQ)
                .regDate(LocalDateTime.now())

                .build();

        studentRepository.save(takeLecture);


        Member memberInfo = memberRepository.findByMno(member.getMno());

        Member memberUpgrade = modelMapper.map(memberInfo, Member.class);
        memberUpgrade.updateRole(MemberRole.STUDENT);
        memberRepository.save(memberUpgrade);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
        updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        // user -> student로 권한변경
//        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Member memberInfo = memberRepository.findByMno(member.getMno());
//        Member memberUpgrade = modelMapper.map(memberInfo, Member.class);
//        memberUpgrade.updateRole(MemberRole.STUDENT);
//        memberRepository.save(memberUpgrade);


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