package com.ddis.ddis_hr.notice.command.application.controller;

import com.ddis.ddis_hr.member.security.CustomUserDetails;
import com.ddis.ddis_hr.notice.command.application.dto.NoticeDTO;
import com.ddis.ddis_hr.notice.command.application.service.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    /** 새 알림 생성 */
    @PostMapping
    public ResponseEntity<NoticeDTO> create(@RequestBody NoticeDTO dto) {
        return ResponseEntity.ok(noticeService.create(dto));
    }

    // 알림 읽음 처리
    @PatchMapping("/{noticeId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long noticeId,
                                           @AuthenticationPrincipal CustomUserDetails user) {
        noticeService.markAsRead(user.getEmployeeId(), noticeId);
        return ResponseEntity.noContent().build();
    }

    /** 알림 삭제 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noticeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
