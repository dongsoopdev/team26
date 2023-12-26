package com.edutech.team26.dto;

import com.edutech.team26.constant.MemberRole;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private Set<MemberRole> roleSet = new HashSet<>();
    private String roleSetStr = "";

}