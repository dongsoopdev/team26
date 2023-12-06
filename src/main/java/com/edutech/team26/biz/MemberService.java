package com.edutech.team26.biz;

import com.edutech.team26.dto.MemberJoinDTO;

public interface MemberService {
    //static class MidExistException extends Exception {}

    boolean join(MemberJoinDTO memberJoinDTO) throws Exception;

    boolean emailAuth(String uuid);

    boolean verifyRecaptcha(String recaptcha);

    boolean updateLoginDate() throws Exception;

    /*MemberJoinDTO myinfo(String email);*/

}