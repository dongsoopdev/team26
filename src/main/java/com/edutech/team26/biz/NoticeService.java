package com.edutech.team26.biz;

import com.edutech.team26.dto.NoticeDTO;

import java.util.List;

public interface NoticeService {
    public List<NoticeDTO> noticeList(Long lecture_no);
    public NoticeDTO findByNno(Long notice_no);
    public Long insertNotice(NoticeDTO noticeDTO);
    public void updateNotice(NoticeDTO noticeDTO);
    public void deleteNotice(Long notice_no);
    public void updateVisited(Long notice_no);
}
