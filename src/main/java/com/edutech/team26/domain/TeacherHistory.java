package com.edutech.team26.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="teacher_history_no")
    private Long teacherHistoryNo;

    @Column(nullable = false)
    private Long mno;

    @Column
    private String reason;

    @Column
    private Boolean activeYn;

    @CreationTimestamp
    private LocalDateTime regDate;

    @UpdateTimestamp
    @Column
    private LocalDateTime activeDate;

}