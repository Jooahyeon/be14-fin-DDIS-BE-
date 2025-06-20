package com.ddis.ddis_hr.notice.command.application.listener;

import com.ddis.ddis_hr.notice.command.application.dto.NoticeDTO;
import com.ddis.ddis_hr.notice.command.application.event.NoticeEvent;
import com.ddis.ddis_hr.notice.command.application.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoticeEventListener {

    private final NoticeService noticeService;

    /**
     * NotificationEvent가 발생하면 DDL에 맞춘 NoticeDTO를 생성해
     * NoticeService.create()로 저장 & WebSocket 전송 처리까지 수행합니다.
     */
    @EventListener
    public void handleNoticeEvent(NoticeEvent event) {
        for (Long empId : event.getTargets()) {
            NoticeDTO dto = NoticeDTO.builder()
                    .noticeContent(event.getContent())
                    .noticeType(event.getReferenceType())
                    .isRead(false)
                    .employeeId(empId)
                    .build();

            noticeService.create(dto);
        }
    }
}
