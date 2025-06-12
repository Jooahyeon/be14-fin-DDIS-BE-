package com.ddis.ddis_hr.review.command.application.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewedPerformanceDTO {
    private Long performanceId;
    private Long goalId;
    private String goalTitle;
    private Double goalValue;
    private Double performanceValue;      // 실적수치
    private Integer reviewerScore;        // 상사평가 점수
    private String reviewerContent;       // 상사평가 의견
    private LocalDateTime reviewerCreatedAt;
}
