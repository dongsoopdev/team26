package com.edutech.team26.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeacherHistoryDTO {

    private Long teacherHistoryNo;

    private Long mno;

    private String reason;

    private Boolean activeYn;

    private LocalDateTime regDate;

    private LocalDateTime activeDate;

}