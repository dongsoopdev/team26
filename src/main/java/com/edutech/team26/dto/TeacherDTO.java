package com.edutech.team26.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeacherDTO {

    private Long teacherNo;
    private Long mno;
    private String fileOriginNm;
    private String fileSaveNm;
    private Boolean activeYn;
    private String status;
    private String intro;
    private LocalDateTime regDate;
    private LocalDateTime activeDate;

}