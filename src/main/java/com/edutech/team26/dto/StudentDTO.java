package com.edutech.team26.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StudentDTO {

    private Long studentNo;
    private Long lectureNo;
    private Long mno;
    private Boolean entranceYn;
    private String status;
    private LocalDateTime regDate;


}