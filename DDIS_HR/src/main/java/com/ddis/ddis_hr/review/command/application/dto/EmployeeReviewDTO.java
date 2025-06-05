package com.ddis.ddis_hr.review.command.application.dto;

import com.ddis.ddis_hr.goals.command.application.dto.GoalDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmployeeReviewDTO {
    private Long employeeId;
    private String employeeName;
    private Long positionId;
    private String positionName;
    private Long teamId;
    private String teamName;
    private Long rankId;
    private String rankName;
    private List<GoalDTO> goals;
}
