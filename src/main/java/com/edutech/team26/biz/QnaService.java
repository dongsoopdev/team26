package com.edutech.team26.biz;

import com.edutech.team26.dto.NoticeDTO;
import com.edutech.team26.dto.QnaCommentDTO;
import com.edutech.team26.dto.QnaDTO;

import java.util.List;

public interface QnaService {
    public List<QnaDTO> qnaListByLec(Long lecture_no);
    public List<QnaDTO> findQnaAll();
    public QnaDTO findByQno(Long qna_no);
    public Long insertQna(QnaDTO qnaDTO);
    public void updateQna(QnaDTO qnaDTO);
    public void deleteQna(Long qna_no);
    public void updateVisited(Long qna_no);

    //댓글
    public List<QnaCommentDTO> commentListByqnoAndLecno(Long qna_no, Long lecture_no);
    public Long insertQnaComment(QnaCommentDTO qnaCommentDTO);
    public void deleteQnaComment(Long comment_no);
}
