package com.ddis.ddis_hr.notice.command.application.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDTO {
    private Long noticeId;
    private String noticeContent;
    private String noticeType;
    private LocalDateTime createdAt;
    private Boolean isRead;
    private Long employeeId;
}
