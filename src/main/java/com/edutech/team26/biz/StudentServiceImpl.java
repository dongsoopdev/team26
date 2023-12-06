package com.edutech.team26.biz;

import com.edutech.team26.constant.MemberRole;
import com.edutech.team26.domain.Member;
import com.edutech.team26.domain.Student;
import com.edutech.team26.dto.MemberSecurityDTO;
import com.edutech.team26.dto.StudentDTO;
import com.edutech.team26.repository.MemberRepository;
import com.edutech.team26.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    private final StudentRepository studentRepository;

    @Override
    public boolean updateGrade(Long mno, Long lectureNo) throws Exception {

        Optional<Member> member = memberRepository.findById(mno);
        if(member.isEmpty()){
            return false;
        }

        Optional<Student> checkStudent = studentRepository.findByMnoAndLectureNo(mno, lectureNo);
        if(checkStudent.isPresent()) {
            return false;
        }

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setMno(mno);
        studentDTO.setLectureNo(lectureNo);
        studentDTO.setEntranceYn(false);        // 선착순으로 진행 예정
        studentDTO.setRegDate(LocalDateTime.now());

        Student student = modelMapper.map(studentDTO, Student.class);

        studentRepository.save(student);

        Member memberInfo = memberRepository.findByMno(mno);

        Member memberUpgrade = modelMapper.map(memberInfo, Member.class);
        memberUpgrade.updateRole(MemberRole.STUDENT);
        memberRepository.save(memberUpgrade);

        return true;
    }

}