package com.edutech.team26.domain;

import com.edutech.team26.model.TakeCourseCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student implements TakeCourseCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentNo;

    @Column(nullable = false,name="lecture_no")
    private Long lectureNo;

    @Column(nullable = false)
    private Long mno;


    @Column(nullable = false, columnDefinition = "bit DEFAULT 0")
    private Boolean entranceYn;

    @Column(columnDefinition = "varchar(50) DEFAULT 'REQ'")
    private String status; // 상태(REQ-수강신청완료, COMPLETE- 수강 중, CANCEL- 수강취소)


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;

}