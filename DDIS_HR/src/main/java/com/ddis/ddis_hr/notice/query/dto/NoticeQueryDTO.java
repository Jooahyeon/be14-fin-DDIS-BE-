package com.ddis.ddis_hr.notice.query.dto;

import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NoticeQueryDTO {
    private Long noticeId;
    private String noticeContent;
    private String noticeType;
    private LocalDateTime createdAt;
    private Boolean isRead;
    private Long employeeId;
}
