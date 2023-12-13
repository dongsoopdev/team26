package com.edutech.team26.biz;

import com.edutech.team26.constant.AcceptCode;
import com.edutech.team26.constant.MemberRole;
import com.edutech.team26.domain.Member;
import com.edutech.team26.domain.Teacher;
import com.edutech.team26.domain.VwLecture;

import com.edutech.team26.dto.TeacherDTO;
import com.edutech.team26.repository.MemberRepository;
import com.edutech.team26.repository.TeacherRepository;

import com.edutech.team26.repository.VwLectureRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    private final TeacherRepository teacherRepository;

    private final VwLectureRepository vwlectureRepository;

    @Override
    public boolean updateGrade(Long mno, MultipartFile uploadFile, HttpServletRequest request) throws Exception {

        Optional<Member> member = memberRepository.findById(mno);
        if(member.isEmpty()){
            return false;
        }

        Optional<Teacher> checkTeacher = teacherRepository.findByMno(mno);
        if(checkTeacher.isPresent()) {
            return false;
        }

        String fileOriginNm = "";
        String fileUploadNm = "";

        if(uploadFile != null) {

            MultipartFile multipartFile = uploadFile;

            ServletContext application = request.getSession().getServletContext();
            String realPath = application.getRealPath("/teacher");

            File uploadPath = new File(realPath);
            if(!uploadPath.exists()) {uploadPath.mkdirs();}

            String originalName = uploadFile.getOriginalFilename();
            fileOriginNm = originalName;

            String uuid = UUID.randomUUID().toString();
            String uploadName = uuid + "_" + originalName;
            fileUploadNm = uploadName;

            multipartFile.transferTo(new File(uploadPath, uploadName));

        }

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setMno(mno);
        teacherDTO.setFileOriginNm(fileOriginNm);
        teacherDTO.setFileSaveNm(fileUploadNm);
        teacherDTO.setActiveYn(false);  // 승인으로 변경 예정
        teacherDTO.setRegDate(LocalDateTime.now());
        teacherDTO.setStatus(AcceptCode.ACCEPT_STATUS_REQ);

        Teacher teacher = modelMapper.map(teacherDTO, Teacher.class);

        teacherRepository.save(teacher);

        Member memberInfo = memberRepository.findByMno(mno);

        Member memberUpgrade = modelMapper.map(memberInfo, Member.class);
        memberUpgrade.updateRole(MemberRole.TEACHER);
        memberRepository.save(memberUpgrade);

        return true;
    }

    @Override
    public boolean changeActive(Long teacherNo, int type) throws Exception {
        Optional<Teacher> result = teacherRepository.findById(teacherNo);
        if(result.isEmpty()) {
            return false;
        }

        if(result.get().getStatus().equals(AcceptCode.ACCEPT_STATUS_OK)) {
            return false;
        }

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setTeacherNo(teacherNo);
        teacherDTO.setMno(result.get().getMno());
        teacherDTO.setFileOriginNm(result.get().getFileOriginNm());
        teacherDTO.setFileSaveNm(result.get().getFileSaveNm());
        teacherDTO.setIntro(result.get().getIntro());
        teacherDTO.setRegDate(result.get().getRegDate());

        switch (type) {
            case 1 :    // 승인
                teacherDTO.setStatus(AcceptCode.ACCEPT_STATUS_OK);
                teacherDTO.setActiveYn(true);
                teacherDTO.setActiveDate(LocalDateTime.now());
                break;
            case 2 :    // 거절
                teacherDTO.setStatus(AcceptCode.ACCEPT_STATUS_REFUSE);
                teacherDTO.setActiveYn(false);
                teacherDTO.setActiveDate(null);
                break;
            default :   // 잘못된 정보
                return false;
        }

        Teacher teacher = modelMapper.map(teacherDTO, Teacher.class);
        teacherRepository.save(teacher);

        return true;
    }

    @Override
    public VwLecture getByMno(long mno) {
        return null;
    }




}