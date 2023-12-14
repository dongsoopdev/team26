package com.edutech.team26.biz;

import com.edutech.team26.domain.Member;
import com.edutech.team26.dto.MemberDTO;
import com.edutech.team26.dto.MemberJoinDTO;

public interface MemberService {
    //static class MidExistException extends Exception {}

    boolean memberDupValidation(String email) throws Exception;

    boolean join(MemberJoinDTO memberJoinDTO) throws Exception;

    boolean emailAuth(String uuid);

    boolean verifyRecaptcha(String recaptcha);

    boolean updateLoginDate() throws Exception;

    MemberDTO getMemberInfo(Long mno);

    /*MemberJoinDTO myinfo(String email);*/

}