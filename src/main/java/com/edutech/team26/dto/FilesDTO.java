package com.edutech.team26.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class FilesDTO {

    private Long fileNo;

    private Long par;

    private String toUse;

    private String fileOriginNm;

    private String fileSaveNm;

    private String fileSaveFolder;

    private LocalDateTime regDate;

    private LocalDateTime activeDate;

}