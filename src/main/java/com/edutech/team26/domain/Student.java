package com.edutech.team26.domain;

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
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentNo;

    @Column(nullable = false)
    private Long lectureNo;

    @Column(nullable = false)
    private Long mno;

    @Column
    private Boolean entranceYn;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;

}