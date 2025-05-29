package com.ddis.ddis_hr.goals.command.application.service;

import com.ddis.ddis_hr.goals.command.application.dto.GoalDTO;
import com.ddis.ddis_hr.goals.command.application.dto.GoalInsertDTO;

import java.util.List;

public interface GoalsService {
    List<GoalDTO> getGoals(Long employeeId);
    Long createGoal(GoalInsertDTO req);
}
