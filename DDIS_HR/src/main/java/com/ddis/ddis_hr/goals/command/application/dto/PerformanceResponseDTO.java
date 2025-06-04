package com.ddis.ddis_hr.goals.command.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PerformanceResponseDTO {
    private Long performanceId;
    private Integer performanceValue;
    private String selfreviewContent;
    private LocalDateTime createdAt;

    // ★ 추가할 필드
    private Long fileId;          // SelfReviewFile PK
    private String originalFileName;      // 원본 파일명
    private String attachmentKey;       // presigned 다운로드 URL
    private String fileType;      // MIME 타입
    private Double fileSize;      // 크기(문자열)
}
