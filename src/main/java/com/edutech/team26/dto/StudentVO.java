package com.edutech.team26.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentVO {

    private Long mno;
    private String email;
    private String userName;
    private String phone;
    private String addr;
    private String addrDetail;
    private String userStatus;
    private String zipcode;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private Long studentNo;
    private Long lectureNo;
    private Boolean entranceYn;

}