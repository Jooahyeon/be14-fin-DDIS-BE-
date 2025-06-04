package com.ddis.ddis_hr.goals.command.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class GoalDTO {
    private Long goalId;
    private String goalTitle;
    private String goalContent;
    private Double goalValue;
    private Double goalWeight;
    private LocalDate goalCreatedAt;
    private Long employeeId;
    private String employeeName;

    private PerformanceDTO performance;
}
