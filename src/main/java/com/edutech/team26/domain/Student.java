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

    @Column(nullable = false)
    private Long lectureNo;

    @Column(nullable = false)
    private Long mno;

    @Column(columnDefinition = "INT DEFAULT n")
    private String entranceYn;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;

}