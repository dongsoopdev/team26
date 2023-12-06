package com.edutech.team26.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentDTO {

    private Long studentNo;
    private Long lectureNo;
    private Long mno;
    private Boolean entranceYn;
    private LocalDateTime regDate;

}