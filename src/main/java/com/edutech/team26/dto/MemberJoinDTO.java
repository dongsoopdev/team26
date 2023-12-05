package com.edutech.team26.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberJoinDTO {

    private String email;
    private String password;
    private String passwordConfirm = "";
    private String userName;
    private String phone;
    private boolean emailAuthYn;
    private LocalDateTime emailAuthTime;
    private String emailAuthKey;
    private String zipcode;
    private String addr;
    private String addrDetail;

}