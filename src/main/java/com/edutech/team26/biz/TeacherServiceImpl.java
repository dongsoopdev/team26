package com.edutech.team26.biz;

import com.edutech.team26.constant.AcceptCode;
import com.edutech.team26.constant.MemberRole;
import com.edutech.team26.domain.*;

import com.edutech.team26.dto.*;
import com.edutech.team26.repository.*;

import com.querydsl.jpa.JPQLQuery;
import jakarta.servlet.ServletContext;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    private final TeacherRepository teacherRepository;

    private final TeacherHistoryRepository teacherHistoryRepository;

    private final VwLectureRepository vwlectureRepository;

    private final FilesRepository filesRepository;

    @Override
    public List<TeacherHistoryFilesVO> getHistoryList(Long mno) throws Exception {
        List<TeacherHistory> teacherHistoryList = teacherHistoryRepository.findByMnoOrderByRegDateDesc(mno);

        List<TeacherHistoryFilesVO> teacherHistoryDTOList = teacherHistoryList.stream()
                .map(teacherHistory -> modelMapper.map(teacherHistory, TeacherHistoryFilesVO.class))
                .collect(Collectors.toList());

        int cnt = 0;
        for(TeacherHistoryFilesVO teacherHistoryFilesVO : teacherHistoryDTOList) {
            List<Files> filesList = filesRepository.findByParAndToUse(teacherHistoryFilesVO.getTeacherHistoryNo(), "teacherApplyFiles");
            List<FilesDTO> filesDTOList = filesList.stream().map(files -> modelMapper.map(files, FilesDTO.class)).collect(Collectors.toList());
            cnt = filesDTOList.size() - 1;
            teacherHistoryFilesVO.setFilesList(filesDTOList);
            teacherHistoryFilesVO.setFileCnt(cnt);
            switch (teacherHistoryFilesVO.getStatus()) {
                case AcceptCode.ACCEPT_STATUS_OK: 
                    teacherHistoryFilesVO.setStatus("승인 완료");
                    break;
                case AcceptCode.ACCEPT_STATUS_REQ :
                    teacherHistoryFilesVO.setStatus("신청 대기");
                    break;
                case AcceptCode.ACCEPT_STATUS_REFUSE:
                    teacherHistoryFilesVO.setStatus("승인 거절");
                    break;
            }
        }
        return teacherHistoryDTOList;
    }

    @Override
    public List<TeacherHistoryVO> getHistoryAllList() throws Exception {

        List<TeacherHistory> teacherHistoryList = teacherHistoryRepository.findAllTeacherHistory();
        List<TeacherHistoryVO> teacherHistoryDTOList = teacherHistoryList.stream()
                .map(teacherHistory -> modelMapper.map(teacherHistory, TeacherHistoryVO.class))
                .collect(Collectors.toList());

        Member member;
        for(TeacherHistoryVO teacherHistoryVO : teacherHistoryDTOList) {

            member = memberRepository.getMemberByMno(teacherHistoryVO.getMno());
            teacherHistoryVO.setEmail(member.getEmail());
            teacherHistoryVO.setUserName(member.getUserName());
            teacherHistoryVO.setPhone(member.getPhone());
            teacherHistoryVO.setUserStatus(member.getUserStatus());

            switch (teacherHistoryVO.getStatus()) {
                case AcceptCode.ACCEPT_STATUS_OK:
                    teacherHistoryVO.setStatus("승인 완료");
                    break;
                case AcceptCode.ACCEPT_STATUS_REQ :
                    teacherHistoryVO.setStatus("신청 대기");
                    break;
                case AcceptCode.ACCEPT_STATUS_REFUSE:
                    teacherHistoryVO.setStatus("승인 거절");
                    break;
            }
        }

        return teacherHistoryDTOList;
    }

    @Override
    public TeacherHistoryFilesVO getHistoryDetail(Long teacherHistoryNo) throws Exception {

        TeacherHistory teacherHistory = teacherHistoryRepository.findByTeacherHistoryNo(teacherHistoryNo);
        TeacherHistoryFilesVO teacherHistoryFilesVO = modelMapper.map(teacherHistory, TeacherHistoryFilesVO.class);

        List<Files> filesList = filesRepository.findByParAndToUse(teacherHistoryFilesVO.getTeacherHistoryNo(), "teacherApplyFiles");
        List<FilesDTO> filesDTOList = filesList.stream().map(files -> modelMapper.map(files, FilesDTO.class)).collect(Collectors.toList());
        teacherHistoryFilesVO.setFilesList(filesDTOList);

        switch (teacherHistoryFilesVO.getStatus()) {
            case AcceptCode.ACCEPT_STATUS_OK:
                teacherHistoryFilesVO.setStatus("승인 완료");
                break;
            case AcceptCode.ACCEPT_STATUS_REQ :
                teacherHistoryFilesVO.setStatus("신청 대기");
                break;
            case AcceptCode.ACCEPT_STATUS_REFUSE:
                teacherHistoryFilesVO.setStatus("승인 거절");
                break;
        }

        return teacherHistoryFilesVO;
    }

    @Override
    public boolean applyGrade(Long mno, List<MultipartFile> uploadFiles, HttpServletRequest request) throws Exception {

        Optional<Member> member = memberRepository.findById(mno);
        if(member.isEmpty()){
            return false;
        }

        String fileOriginNm = "";
        String fileUploadNm = "";
        Long teacherNo = 0L;

        Optional<Teacher> checkTeacher = teacherRepository.findByMno(mno);

        if(checkTeacher.isPresent()) {
            teacherNo = checkTeacher.get().getTeacherNo();
        } else {
            TeacherDTO teacherDTO = new TeacherDTO();
            teacherDTO.setMno(mno);
            teacherDTO.setActiveYn(false);
            teacherDTO.setRegDate(LocalDateTime.now());
            teacherDTO.setStatus(AcceptCode.ACCEPT_STATUS_REQ);

            Teacher teacher = modelMapper.map(teacherDTO, Teacher.class);

            teacherRepository.save(teacher);

            Optional<Teacher> insertTeacher = teacherRepository.findByMno(mno);
            teacherNo = insertTeacher.get().getTeacherNo();

        }

        TeacherHistoryDTO teacherHistoryDTO = new TeacherHistoryDTO();
        teacherHistoryDTO.setMno(mno);
        teacherHistoryDTO.setStatus(AcceptCode.ACCEPT_STATUS_REQ);
        TeacherHistory teacherHistory = modelMapper.map(teacherHistoryDTO, TeacherHistory.class);
        Long teacherHistoryNo = teacherHistoryRepository.save(teacherHistory).getTeacherHistoryNo();

        /*ServletContext application = request.getSession().getServletContext();
        String realPath = application.getRealPath("/teacher/" + teacherNo);*/

        if(uploadFiles != null) {

            for(MultipartFile multipartFile : uploadFiles) {
                String uploadPathStr = "C:/upload/teacher/" + teacherNo;
                File uploadPath = new File(uploadPathStr);
                if(!uploadPath.exists()) {uploadPath.mkdirs();}

                String originalName = multipartFile.getOriginalFilename();
                fileOriginNm = originalName;

                String uuid = UUID.randomUUID().toString();
                String uploadName = uuid + "_" + originalName;
                fileUploadNm = uploadName;

                multipartFile.transferTo(new File(uploadPath, uploadName));

                FilesDTO filesDTO = new FilesDTO();
                filesDTO.setPar(teacherHistoryNo);
                filesDTO.setToUse("teacherApplyFiles");
                filesDTO.setFileOriginNm(fileOriginNm);
                filesDTO.setFileSaveNm(fileUploadNm);
                filesDTO.setFileSaveFolder(uploadPathStr);
                Files files = modelMapper.map(filesDTO, Files.class);
                filesRepository.save(files);
            }

        }

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
    public boolean historyRemove(HttpServletRequest request, Long teacherHistoryNo) throws Exception {
        Optional<TeacherHistory> optionalTeacherHistory = teacherHistoryRepository.findById(teacherHistoryNo);
        if(optionalTeacherHistory.isEmpty()) {
            return false;
        }

        TeacherHistory teacherHistory = optionalTeacherHistory.get();

        List<Files> filesList = filesRepository.findByParAndToUse(teacherHistoryNo, "teacherApplyFiles");
        if(filesList != null) {
            for(Files files : filesList) {
                File file = new File( files.getFileSaveFolder() + File.separator + files.getFileSaveNm());
                if (file.exists()) { // 해당 파일이 존재하면
                    file.delete(); // 파일 삭제
                    filesRepository.deleteById(files.getFileNo());
                }
            }
        }

        teacherHistoryRepository.deleteById(teacherHistoryNo);

        return true;
    }

    @Override
    public boolean upgradeGrade(Long teacherHistoryNo, String status, String reason) throws Exception {

        Optional<TeacherHistory> optionalTeacherHistory = teacherHistoryRepository.findById(teacherHistoryNo);
        if(optionalTeacherHistory.isEmpty()) {
            return false;
        }

        if(status.equals(AcceptCode.ACCEPT_STATUS_OK)) {
            // 승일일때
            // teacherHistory
            LocalDateTime now = LocalDateTime.now();

            TeacherHistory teacherHistory = optionalTeacherHistory.get();
            teacherHistory.changeStatus(AcceptCode.ACCEPT_STATUS_OK, reason, now);
            Long saveTeacherHistoryNo = teacherHistoryRepository.save(teacherHistory).getTeacherHistoryNo();

            TeacherHistory saveTeacherHistory = teacherHistoryRepository.findByTeacherHistoryNo(saveTeacherHistoryNo);

            Long mno = saveTeacherHistory.getMno();

            // teacher
            Optional<Teacher> optionalTeacher = teacherRepository.findByMno(mno);
            if(optionalTeacher.isEmpty()) {
                return false;
            }
            Teacher teacher = optionalTeacher.get();
            teacher.upgradeStatus(true, AcceptCode.ACCEPT_STATUS_OK, now);
            teacherRepository.save(teacher);

            List<Files> filesList = filesRepository.findByParAndToUse(saveTeacherHistoryNo, "teacherApplyFiles");

            // 파일 히스토리에 해당하는 넘버 다시 저장 teacherApplyOKFiles 로 저장
            for(Files files : filesList) {
                FilesDTO filesDTO = new FilesDTO();
                filesDTO.setPar(teacher.getTeacherNo());
                filesDTO.setToUse("teacherApplyFilesOK");
                filesDTO.setFileOriginNm(files.getFileOriginNm());
                filesDTO.setFileSaveNm(files.getFileSaveNm());
                filesDTO.setFileSaveFolder(files.getFileSaveFolder());
                Files saveFiles = modelMapper.map(filesDTO, Files.class);
                filesRepository.save(saveFiles);
            }

            //선생님 승인 이후에 권한 업데이트 작업
            Member memberInfo = memberRepository.findByMno(mno);

            Member memberUpgrade = modelMapper.map(memberInfo, Member.class);
            memberUpgrade.updateRole(MemberRole.TEACHER);
            memberRepository.save(memberUpgrade);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
            updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
            Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(newAuth);

        } else {
            // 거절일때
            // teacherHistory
            // status = REFUSE
            TeacherHistory teacherHistory = optionalTeacherHistory.get();
            teacherHistory.changeStatus(AcceptCode.ACCEPT_STATUS_REFUSE, reason, null);
            teacherHistoryRepository.save(teacherHistory);
        }

        return true;
    }

    @Override
    public List<TeacherVO> teacherList() {
        List<VwTeacher> vwTeacherList = teacherRepository.teacherList();
        List<TeacherVO> teacherList = vwTeacherList.stream().map(teacher
                        -> modelMapper.map(teacher, TeacherVO.class))
                .collect(Collectors.toList());
        return teacherList;
    }

    @Override
    public VwLecture getByMno(long mno) {
        return null;
    }




}