package com.ddis.ddis_hr.review.query.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PerformanceHistoryDTO {
    private Long employeeId;
    private String employeeName;
    private String rankName;
    private String departmentName;
    private String teamName;
    private String yearMonth; // e.g., "2024-12"
    private String performanceContent;
    private Double performanceValue;
    private Integer reviewScore; // 평가 점수
}
