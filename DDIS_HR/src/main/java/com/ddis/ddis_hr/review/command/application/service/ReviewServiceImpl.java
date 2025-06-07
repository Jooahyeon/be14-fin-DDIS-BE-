package com.ddis.ddis_hr.review.command.application.service;

import com.ddis.ddis_hr.employee.command.domain.repository.EmployeeRepository;
import com.ddis.ddis_hr.goals.command.application.dto.GoalDTO;
import com.ddis.ddis_hr.goals.command.application.mapper.GoalsMapper;
import com.ddis.ddis_hr.goals.command.domain.aggregate.Goals;
import com.ddis.ddis_hr.goals.command.domain.aggregate.Performance;
import com.ddis.ddis_hr.goals.command.domain.repository.GoalsRepository;
import com.ddis.ddis_hr.goals.command.domain.repository.PerformanceRepository;
import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import com.ddis.ddis_hr.review.command.application.dto.EmployeeReviewDTO;
import com.ddis.ddis_hr.review.command.application.mapper.EmployeeReviewMapper;
import com.ddis.ddis_hr.review.command.domain.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final EmployeeReviewMapper employeeReviewMapper;
    private final ReviewRepository reviewRepository;
    private final EmployeeRepository employeeRepository;
    private final PerformanceRepository performanceRepository;
    private final GoalsRepository goalsRepository;
    private final GoalsMapper goalsMapper;

    public ReviewServiceImpl(EmployeeReviewMapper employeeReviewMapper, ReviewRepository reviewRepository, EmployeeRepository employeeRepository,GoalsRepository goalsRepository ,PerformanceRepository performanceRepository, GoalsMapper goalsMapper) {
        this.employeeReviewMapper = employeeReviewMapper;
        this.reviewRepository = reviewRepository;
        this.employeeRepository = employeeRepository;
        this.goalsRepository = goalsRepository;
        this.performanceRepository = performanceRepository;
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
    @Override
    public void saveEvaluation(
            Long performanceId,
            Long employeeIdReviewer,
            Integer reviewerScore,
            String reviewerContent,
            String decision
    ) {
        // 1) performanceId로 기존 Performance 조회
        Performance perf = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 자기평가 레코드가 없습니다. id=" + performanceId
                ));

        // 2) 상사(Reviewer) 엔티티 조회
        Employee reviewer = employeeRepository.findById(employeeIdReviewer)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 평가자(상사)가 없습니다. id=" + employeeIdReviewer
                ));

        // 3) 상사평가 필드만 업데이트
        perf.setReviewer(reviewer);
        perf.setReviewerScore(reviewerScore);

        // 리뷰 작성 시각(또는 승인 시각)
        perf.setReviewerCreatedAt(LocalDateTime.now());

        // 리뷰 의견
        perf.setReviewerContent(reviewerContent);

        // 상태 업데이트 (승인/반려)
        if ("승인".equalsIgnoreCase(decision)) {
            perf.setStatus(Performance.Status.승인);
            perf.setApproveDate(LocalDateTime.now());
        } else if ("반려".equalsIgnoreCase(decision)) {
            perf.setStatus(Performance.Status.반려);
        } else {
            // decision 값이 “승인”/“반려” 외면 예외 처리
            throw new IllegalArgumentException("decision은 '승인' 또는 '반려'여야 합니다.");
        }

        // 4) save → JPA가 ID가 있으니 UPDATE 쿼리 실행
        performanceRepository.save(perf);
    }
}
