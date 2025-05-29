package com.ddis.ddis_hr.goals.command.application.mapper;

import com.ddis.ddis_hr.goals.command.application.dto.GoalDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface GoalsMapper {
    List<GoalDTO> findGoalsByEmployeeId(Long employeeId);
}
