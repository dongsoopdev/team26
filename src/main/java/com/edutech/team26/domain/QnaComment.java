package com.edutech.team26.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

//    create table notice_comment(
//            cno INT PRIMARY KEY AUTO_INCREMENT,
//            nno INT,
//            author VARCHAR(16) NOT NULL,
//            resdate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
//            content VARCHAR(200),
//            FOREIGN KEY(nno) REFERENCES notice(nno) ON DELETE CASCADE
//            );
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EntityListeners(value = {AuditingEntityListener.class})
public class QnaComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_no;

    @Column(nullable = false)
    private Long qna_no;

    @Column(nullable = false)
    private Long lecture_no;

    @Column(length = 2000, nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "regdate", nullable = false)
    private LocalDateTime regDate;

    @Column(nullable = false)
    private Long mno;
}
