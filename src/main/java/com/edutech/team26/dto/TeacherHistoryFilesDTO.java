package com.edutech.team26.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TeacherHistoryFilesDTO {

    private Long teacherHistoryNo;

    private Long mno;

    private String reason;

    private Boolean activeYn;

    private LocalDateTime regDate;

    private LocalDateTime activeDate;

    private List<FilesDTO> filesList;

}