package com.edutech.team26.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDTO {

    private Long mno;
    private String email;
    private String password;
    private String userName;
    private String phone;
    private LocalDateTime resetPasswordLimitTime;
    private String resetPasswordKey;
    private String zipcode;
    private String addr;
    private String addrDetail;
    private LocalDateTime lastLoginAt;
    private LocalDateTime regDate;
    private String userStatus;

}