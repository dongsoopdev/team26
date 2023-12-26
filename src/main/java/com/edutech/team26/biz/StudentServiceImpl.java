package com.edutech.team26.biz;

import com.edutech.team26.constant.MemberRole;
import com.edutech.team26.domain.Lecture;
import com.edutech.team26.domain.Member;
import com.edutech.team26.domain.Student;
import com.edutech.team26.domain.VwCourse;
import com.edutech.team26.dto.StudentDTO;
import com.edutech.team26.repository.MemberRepository;
import com.edutech.team26.repository.StudentRepository;
import com.edutech.team26.repository.VwCourseRepository;
import com.edutech.team26.repository.VwLectureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    private final StudentRepository studentRepository;

    private final VwLectureRepository vwLectureRepository;
    private final VwCourseRepository vwCourseRepository;
/*    @Override
    public boolean applyStudent(Long mno, Long lectureNo) throws Exception {

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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
        updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return true;
    }*/


    @Override
    public boolean applyStudent(Long mno, Long lectureNo) throws Exception {

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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
        updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return true;
    }


    //수강신청 취소
    @Override
    public void deleteCourse(long studentNo) {
        Student student =studentRepository.getById(studentNo);

        // 기존 학생 엔터티의 상태를 "CANCEL"로 변경
        student.setStatus("CANCEL");
        studentRepository.save(student);
    }

    @Override
    public void updateEntranceStatus(Long studentNo) {
        Student student = studentRepository.getById(studentNo);
        //System.out.println("여기야!!!!!!!"+student.getEntranceYn());
       // student.updateEntranceYn(1);

        VwCourse stu=  vwCourseRepository.getById(studentNo);

                // Check if the student is late
        LocalDateTime zoomDate = stu.getZoomDate();
        LocalDateTime currentTime = LocalDateTime.now();

        if (currentTime.isAfter(zoomDate)) {
            student.updateEntranceYn(2); // 지각
        } else {
            student.updateEntranceYn(1); // 입장완료
        }


        studentRepository.save(student);
        System.out.println("여기야222222222222!!!!!!!"+student.getEntranceYn());

    }

}