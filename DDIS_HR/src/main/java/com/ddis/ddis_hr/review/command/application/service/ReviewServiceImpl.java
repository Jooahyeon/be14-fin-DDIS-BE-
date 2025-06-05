package com.ddis.ddis_hr.review.command.application.service;

import com.ddis.ddis_hr.employee.command.domain.repository.EmployeeRepository;
import com.ddis.ddis_hr.goals.command.application.dto.GoalDTO;
import com.ddis.ddis_hr.goals.command.application.mapper.GoalsMapper;
import com.ddis.ddis_hr.review.command.application.dto.EmployeeReviewDTO;
import com.ddis.ddis_hr.review.command.application.mapper.EmployeeReviewMapper;
import com.ddis.ddis_hr.review.command.domain.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final EmployeeReviewMapper employeeReviewMapper;
    private final ReviewRepository reviewRepository;
    private final EmployeeRepository employeeRepository;
    private final GoalsMapper goalsMapper;

    public ReviewServiceImpl(EmployeeReviewMapper employeeReviewMapper, ReviewRepository reviewRepository, EmployeeRepository employeeRepository, GoalsMapper goalsMapper) {
        this.employeeReviewMapper = employeeReviewMapper;
        this.reviewRepository = reviewRepository;
        this.employeeRepository = employeeRepository;
        this.goalsMapper = goalsMapper;
    }

    @Override
    public List<EmployeeReviewDTO> getTeamReview(Long teamId) {
        // 1) teamId로 직원 목록 가져오기
        List<EmployeeReviewDTO> employees = employeeReviewMapper.findEmployeeByTeamId(teamId);

        // 2) 직원별로 목표 조회
        for (EmployeeReviewDTO emp : employees) {
            List<GoalDTO> goalList = goalsMapper.findGoalsByEmployeeId(emp.getEmployeeId());
            emp.setGoals(goalList);
        }

        return employees;
    }
}
