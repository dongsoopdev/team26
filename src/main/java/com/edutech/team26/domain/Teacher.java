package com.edutech.team26.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherNo;

    @Column(nullable = false)
    private Long mno;

    @Column
    private String fileOriginNm;

    @Column
    private String fileSaveNm;

    @Column
    private Boolean activeYn;

    @Column
    private String status;

    @Column
    private String intro;

    @Column
    private LocalDateTime regDate;

    @Column
    private LocalDateTime activeDate;


}