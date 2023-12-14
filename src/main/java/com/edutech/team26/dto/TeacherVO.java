
package com.edutech.team26.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class TeacherVO {

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
    private Long teacherNo;
    private String fileOriginNm;
    private String fileSaveNm;
    private Boolean activeYn;
    private LocalDateTime activeDate;
    private String status;
    private String intro;

}
