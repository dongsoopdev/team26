package com.edutech.team26.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QnaDTO {
    private Long qna_no;
    private Long lecture_no;
    private String title;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private int visited;
    private Long mno;
    private String lecture_name;
}
