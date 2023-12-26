package com.edutech.team26.biz;

import com.edutech.team26.domain.Member;
import com.edutech.team26.dto.MemberDTO;
import com.edutech.team26.dto.MemberJoinDTO;
import com.edutech.team26.dto.MemberPwDTO;

import java.util.List;

public interface MemberService {
    //static class MidExistException extends Exception {}

    boolean memberDupValidation(String email) throws Exception;

    boolean join(MemberJoinDTO memberJoinDTO) throws Exception;

    boolean modifyInfo(Long mno, MemberDTO memberDTO) throws Exception;

    boolean modifyPw(Long mno, MemberPwDTO memberPwDTO) throws Exception;

    boolean withdraw(Long mno) throws Exception;

    boolean emailAuth(String uuid);

    boolean verifyRecaptcha(String recaptcha);

    boolean updateLoginDate() throws Exception;

    MemberDTO getMemberInfo(Long mno);

    MemberDTO findId(String username, String phone);

    List<MemberDTO> getAllList();

    boolean findPw(String email, String phone, String username);

    boolean resetPwAuth(Long mno, String key);

    boolean resetPwAuthPro(Long mno, String key, String Password);

    String getMemberGrade(String type);

}