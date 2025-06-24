package com.ddis.ddis_hr.notice.command.application.service;

import com.ddis.ddis_hr.employee.command.domain.repository.EmployeeRepository;
import com.ddis.ddis_hr.employee.command.domain.repository.EmployeesRepository;
import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import com.ddis.ddis_hr.notice.command.application.dto.NoticeDTO;
import com.ddis.ddis_hr.notice.command.domain.aggregate.NoticeEntity;
import com.ddis.ddis_hr.notice.command.domain.repository.NoticeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final EmployeeRepository employeeRepository;

    public NoticeServiceImpl(NoticeRepository noticeRepository, SimpMessagingTemplate messagingTemplate, EmployeeRepository employeeRepository) {
        this.noticeRepository = noticeRepository;
        this.messagingTemplate = messagingTemplate;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public NoticeDTO create(NoticeDTO dto) {
        // 1) DB에서 영속 상태의 Employee 조회
        Employee emp = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() ->
                        new EntityNotFoundException("해당 사원을 찾을 수 없습니다. id=" + dto.getEmployeeId())
                );

        // 2) NoticeEntity에 세팅
        NoticeEntity entity = NoticeEntity.builder()
                .noticeContent(dto.getNoticeContent())
                .noticeType(dto.getNoticeType())
                .isRead(dto.getIsRead() != null ? dto.getIsRead() : false)
                .employee(emp)          // managed 상태의 emp
                .build();

        // 3) 저장
        NoticeEntity saved = noticeRepository.save(entity);

        // WebSocket 알림 발송
        String destination = "/topic/notice/" + dto.getEmployeeId();
        messagingTemplate.convertAndSend(destination, toDto(saved));

        return toDto(saved);
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
