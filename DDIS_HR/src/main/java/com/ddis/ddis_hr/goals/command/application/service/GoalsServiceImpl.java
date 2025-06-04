package com.ddis.ddis_hr.goals.command.application.service;

import com.ddis.ddis_hr.employee.command.domain.repository.EmployeeRepository;
import com.ddis.ddis_hr.goals.command.application.dto.GoalDTO;
import com.ddis.ddis_hr.goals.command.application.dto.GoalInsertDTO;
import com.ddis.ddis_hr.goals.command.application.mapper.GoalsMapper;
import com.ddis.ddis_hr.goals.command.domain.aggregate.Goals;
import com.ddis.ddis_hr.goals.command.domain.repository.GoalsRepository;
import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class GoalsServiceImpl implements GoalsService {
    private GoalsMapper goalsMapper;
    private GoalsRepository goalsRepository;
    private EmployeeRepository employeeRepository;

    public GoalsServiceImpl(GoalsMapper goalsMapper, GoalsRepository goalsRepository, EmployeeRepository employeeRepository) {
        this.goalsMapper = goalsMapper;
        this.goalsRepository = goalsRepository;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public List<GoalDTO> getGoals(Long employeeId) {
        return goalsMapper.findGoalsByEmployeeId(employeeId);


    }


    @Override
    public Long createGoal(GoalInsertDTO req) {
        Employee emp = employeeRepository.getReferenceById(req.getEmployeeId());

        Goals saved = goalsRepository.save(
                Goals.builder()
                        .goalTitle(req.getGoalTitle())
                        .goalContent(req.getGoalContent())
                        .goalValue(req.getGoalValue())
                        .goalWeight(req.getGoalWeight())
                        .goalCreatedAt(LocalDateTime.now())
                        .employeeId(emp)
                        .build());
        return saved.getGoalId();
    }
}