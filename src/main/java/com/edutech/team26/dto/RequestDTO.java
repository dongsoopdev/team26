package com.edutech.team26.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
    private Long request_no;
    private Long lecture_no;
    private String title;
    private String content;
    //private LocalDateTime regDate;
    //private LocalDateTime modDate;
    private Long mno;
}
