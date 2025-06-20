package com.ddis.ddis_hr.notice.command.application.service;

import com.ddis.ddis_hr.notice.command.application.dto.NoticeDTO;

public interface NoticeService {
    NoticeDTO create(NoticeDTO dto);
    NoticeDTO markAsRead(Long employeeId, Long noticeId);
    void delete(Long noticeId);
}
