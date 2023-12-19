package com.edutech.team26.biz;

import com.edutech.team26.domain.Member;
import com.edutech.team26.domain.Notice;
import com.edutech.team26.dto.MemberDTO;
import com.edutech.team26.dto.MemberSecurityDTO;
import com.edutech.team26.dto.NoticeDTO;
import com.edutech.team26.repository.MemberRepository;
import com.edutech.team26.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;

    //특정 강의 공지사항 리스트
    @Override
    public List<NoticeDTO> noticeList(Long lecture_no) {
        List<Notice> list = noticeRepository.findAll();
        // lecture_no를 기반으로 공지사항 필터링
        List<NoticeDTO> noticeList = list.stream()
                .filter(notice -> notice.getLecture_no().equals(lecture_no))
                .map(notice -> modelMapper.map(notice, NoticeDTO.class))
                .collect(Collectors.toList());
        return noticeList;
    }

    //모든 공지사항 리스트
    @Override
    public List<NoticeDTO> findNoticeAll() {
        List<Notice> lst = noticeRepository.findAll();
        List<NoticeDTO> noticeList = lst.stream().map(notice
                        -> modelMapper.map(notice, NoticeDTO.class))
                .collect(Collectors.toList());
        return noticeList;
    }

    @Override
    public NoticeDTO findByNno(Long notice_no) {
        Optional<Notice> noti = noticeRepository.findById(notice_no);
        NoticeDTO notice = modelMapper.map(noti, NoticeDTO.class);
        return notice;
    }

    @Override
    public Long insertNotice(NoticeDTO noticeDTO) {
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        noticeDTO.setMno(member.getMno());
        Notice notice = modelMapper.map(noticeDTO, Notice.class);
        Long notice_no = noticeRepository.save(notice).getNotice_no();
        return notice_no;
    }

    @Override
    public void updateNotice(NoticeDTO noticeDTO) {
        Optional<Notice> noti = noticeRepository.findById(noticeDTO.getNotice_no());
        Notice notice = noti.orElseThrow();
        notice.change(noticeDTO.getTitle(), noticeDTO.getContent());
        noticeRepository.save(notice);
    }

    @Override
    public void deleteNotice(Long notice_no) {
        noticeRepository.deleteById(notice_no);
    }

    @Override
    public void updateVisited(Long notice_no) {
        noticeRepository.updateNoticeByVisited(notice_no);
    }
}
