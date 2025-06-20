package com.ddis.ddis_hr.notice.query.service;

import com.ddis.ddis_hr.notice.query.dao.NoticeMapper;
import com.ddis.ddis_hr.notice.query.dto.NoticeQueryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeQueryService {

    private final NoticeMapper noticeMapper;

    public List<NoticeQueryDTO> getByEmployeeId(Long employeeId) {
        return noticeMapper.selectByEmployeeId(employeeId);
    }
}
