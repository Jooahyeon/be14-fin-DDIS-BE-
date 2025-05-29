package com.ddis.ddis_hr.goals.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoalInsertDTO {
    private String goalTitle;
    private String goalContent;
    private Integer goalValue;
    private Integer goalWeight;
    private LocalDateTime goalCreatedAt;
    private Long employeeId;
}
