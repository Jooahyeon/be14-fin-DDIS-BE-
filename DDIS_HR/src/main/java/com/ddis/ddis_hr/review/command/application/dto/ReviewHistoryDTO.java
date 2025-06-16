package com.ddis.ddis_hr.review.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewHistoryDTO {
    private String yearMonth;             // ex) "2025-06"
    private String performanceDescription;// 목표 내용 (goalContent)
    private Integer goalValue;            // 목표 수치
    private Integer performanceValue;     // 실적 수치 (selfreview.performance_value)
    private String selfReviewContent;     // 자기평가 의견 (selfreview.selfreview_content)
    private Integer reviewScore;
}
