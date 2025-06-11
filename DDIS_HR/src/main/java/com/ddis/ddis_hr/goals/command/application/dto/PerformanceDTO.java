package com.ddis.ddis_hr.goals.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceDTO {
    private Long performanceId;
    private LocalDateTime selfreviewCreateAt;
    private Integer performanceValue;
    private String selfreviewContent;

    // S3에서 받아온 presigned GET URL 목록
    private List<String> attachmentKeys;
    private List<String> attachmentFileNames;
    private List<String> attachmentFileTypes;
    private List<Long> attachmentFileSizes;
    private Integer reviewerScore;
    private String reviewerContent;
    private Long employeeIdReviewer;



}
