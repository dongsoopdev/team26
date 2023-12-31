package com.edutech.team26.biz;

import com.edutech.team26.domain.Notice;
import com.edutech.team26.domain.Qna;
import com.edutech.team26.domain.QnaComment;
import com.edutech.team26.dto.*;
import com.edutech.team26.repository.MemberRepository;
import com.edutech.team26.repository.QnaCommentRepository;
import com.edutech.team26.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QnaServiceImpl implements QnaService{
    private final QnaRepository qnaRepository;
    private final QnaCommentRepository qnaCommentRepository;
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    //특정 강의 qna 리스트
    @Override
    public List<QnaDTO> qnaListByLec(Long lecture_no) {
        List<Qna> list = qnaRepository.findAll();
        // lecture_no를 기반으로 공지사항 필터링
        List<QnaDTO> qnaList = list.stream()
                .filter(qna -> qna.getLecture_no().equals(lecture_no))
                .map(qna -> modelMapper.map(qna, QnaDTO.class))
                .collect(Collectors.toList());
        return qnaList;
    }

    //모든 qna 리스트
    @Override
    public List<QnaDTO> findQnaAll() {
        List<Qna> list = qnaRepository.findAll();
        List<QnaDTO> qnaList = list.stream().map(
                qna -> modelMapper.map(qna,QnaDTO.class))
                .collect(Collectors.toList());
        return qnaList;
    }

    @Override
    public QnaDTO findByQno(Long qna_no) {
        Optional<Qna> qna = qnaRepository.findById(qna_no);
        QnaDTO qnadto = modelMapper.map(qna, QnaDTO.class);
        return qnadto;
    }

    @Override
    public Long insertQna(QnaDTO qnaDTO) {
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        qnaDTO.setMno(member.getMno());
        Qna qna = modelMapper.map(qnaDTO, Qna.class);
        Long qna_no = qnaRepository.save(qna).getQna_no();
        return qna_no;
    }

    @Override
    public void updateQna(QnaDTO qnaDTO) {
        Optional<Qna> q = qnaRepository.findById(qnaDTO.getQna_no());
        Qna qna = q.orElseThrow();
        qna.change(qnaDTO.getTitle(), qnaDTO.getContent());
        qnaRepository.save(qna);
    }

    @Override
    public void deleteQna(Long qna_no) {
        qnaRepository.deleteById(qna_no);
    }

    @Override
    public void updateVisited(Long qna_no) {
        qnaRepository.updateQnaByVisited(qna_no);
    }

    @Override
    public List<QnaCommentDTO> commentListByqnoAndLecno(Long qna_no, Long lecture_no) {
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<QnaComment> list = qnaCommentRepository.findAll();
        // qna_no와 lecture_no 기반으로 공지사항 필터링
        List<QnaCommentDTO> commentList = list.stream()
                .filter(comment -> comment.getQna_no().equals(qna_no) && comment.getLecture_no().equals(lecture_no))
                .map(comment -> {
                    MemberDTO memberDTO = memberService.getMemberInfo(comment.getMno());
                    QnaCommentDTO dto = new QnaCommentDTO();
                    dto.setUserName(memberDTO.getUserName());
                    modelMapper.map(comment, dto);
                    return dto;
                })
                    .collect(Collectors.toList());

        return commentList;
    }

    @Override
    public Long insertQnaComment(QnaCommentDTO qnaCommentDTO) {
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        qnaCommentDTO.setMno(member.getMno());
        QnaComment comment = modelMapper.map(qnaCommentDTO, QnaComment.class);
        Long comment_no = qnaCommentRepository.save(comment).getComment_no();
        return comment_no;
    }

    @Override
    public void deleteQnaComment(Long comment_no) {
        qnaCommentRepository.deleteById(comment_no);
    }
}
