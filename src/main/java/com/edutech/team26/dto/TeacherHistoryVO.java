package com.edutech.team26.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeacherHistoryVO {

    private Long teacherHistoryNo;

    private Long mno;

    private String reason;

    private String status;

    private LocalDateTime activeDate;

    private LocalDateTime regDate;

    private String email;

    private String userName;

    private String phone;

    private String userStatus;

}