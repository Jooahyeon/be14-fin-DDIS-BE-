package com.ddis.ddis_hr.review.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewHistoryDTO {
    private String yearMonth;             // ex: "2025-05"
    private String performanceDescription; // 목표/성과
    private Integer performanceValue;
    private Double reviewScore;
}
