package com.edutech.team26.domain;

import com.edutech.team26.constant.TakeCourseCode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@ToString
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


    @Column(nullable = false, columnDefinition = "int DEFAULT 0")
    private int entranceYn; // 0.입장 전 1. 입장완료 , 2. 지각, 3. 결석

    @Column(columnDefinition = "varchar(50) DEFAULT 'REQ'")
    private String status; // 상태(REQ-수강신청완료, COMPLETE- 수강 중, CANCEL- 수강취소)


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;

    @UpdateTimestamp
    private LocalDateTime modDate;


    public void updateEntranceYn(int entranceYn){
        this.entranceYn=entranceYn;
        this.modDate = LocalDateTime.now();
    }

}