package com.ddis.ddis_hr.goals.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
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

    private List<PerformanceDTO> performances;
}
