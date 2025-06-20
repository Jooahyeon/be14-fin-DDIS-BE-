package com.ddis.ddis_hr.notice.command.application.service;

import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import com.ddis.ddis_hr.notice.command.application.dto.NoticeDTO;
import com.ddis.ddis_hr.notice.command.domain.aggregate.NoticeEntity;
import com.ddis.ddis_hr.notice.command.domain.repository.NoticeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NoticeServiceImpl(NoticeRepository noticeRepository, SimpMessagingTemplate messagingTemplate) {
        this.noticeRepository = noticeRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public NoticeDTO create(NoticeDTO dto) {
        // Employee 엔티티 참조만 할 경우, 필요한 경우 Repository.getReferenceById 로 교체하세요.
        Employee emp = new Employee();
        emp.setEmployeeId(dto.getEmployeeId());

        NoticeEntity entity = NoticeEntity.builder()
                .noticeContent(dto.getNoticeContent())
                .noticeType(dto.getNoticeType())
                .isRead(dto.getIsRead() != null ? dto.getIsRead() : false)
                .employee(emp)
                .build();

        NoticeEntity saved = noticeRepository.save(entity);

        NoticeDTO result = toDto(saved);

        // WebSocket 전송
        String destination = "/topic/notice/" + dto.getEmployeeId();
        messagingTemplate.convertAndSend(destination, result);

        return result;
    }


    @Override
    public NoticeDTO markAsRead(Long employeeId, Long noticeId) {
        NoticeEntity notice = noticeRepository
                .findByNoticeIdAndEmployeeEmployeeId(noticeId, employeeId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "해당 알림을 찾을 수 없습니다. noticeId=" + noticeId + ", employeeId=" + employeeId));

        // 읽음 처리
        notice.setIsRead(true);
        NoticeEntity saved = noticeRepository.save(notice);

        return toDto(saved);
    }

    @Override
    public void delete(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }

    private NoticeDTO toDto(NoticeEntity e) {
        return NoticeDTO.builder()
                .noticeId(e.getNoticeId())
                .noticeContent(e.getNoticeContent())
                .noticeType(e.getNoticeType())
                .isRead(e.getIsRead())
                .employeeId(e.getEmployee().getEmployeeId())
                .build();
    }
}
