package com.edutech.team26.biz;

import com.edutech.team26.dto.MemberJoinDTO;

public interface MemberService {
    //static class MidExistException extends Exception {}

    boolean join(MemberJoinDTO memberJoinDTO, String userType) throws Exception;

    boolean emailAuth(String uuid);

    boolean verifyRecaptcha(String recaptcha);

    MemberJoinDTO myinfo(String email);

}