package com.ddis.ddis_hr.goals.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceReviewDTO {
    private Long performanceId;
    private Long goalId;
    private String goalTitle;
    private Double performanceValue;
    private Integer reviewerScore;
    private String reviewerContent;
    private LocalDateTime reviewerCreatedAt;
}
