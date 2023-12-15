package com.edutech.team26.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EntityListeners(value = {AuditingEntityListener.class})
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notice_no;

    @Column(nullable = false)
    private Long lecture_no;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name="regdate", nullable = false)
    private LocalDateTime regDate;

    @UpdateTimestamp
    @Column(name = "moddate")
    private LocalDateTime modDate;

    @Column(nullable = false)
    private int visited;
    @Column(nullable = false)
    private Long mno;

    //update를 위한 메서드
    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }
}
