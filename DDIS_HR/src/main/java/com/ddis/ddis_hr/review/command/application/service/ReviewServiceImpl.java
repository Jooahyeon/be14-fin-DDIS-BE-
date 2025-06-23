package com.ddis.ddis_hr.review.command.application.service;

import com.ddis.ddis_hr.employee.command.domain.repository.EmployeeRepository;
import com.ddis.ddis_hr.goals.command.application.dto.GoalDTO;
import com.ddis.ddis_hr.goals.command.application.dto.PerformanceReviewDTO;
import com.ddis.ddis_hr.goals.command.application.mapper.GoalsMapper;
import com.ddis.ddis_hr.goals.command.domain.aggregate.Performance;
import com.ddis.ddis_hr.goals.command.domain.repository.PerformanceRepository;
import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import com.ddis.ddis_hr.review.command.application.dto.EmployeeReviewDTO;
import com.ddis.ddis_hr.review.command.application.dto.ReviewedPerformanceDTO;
import com.ddis.ddis_hr.review.command.application.mapper.EmployeeReviewMapper;
import com.ddis.ddis_hr.review.command.application.mapper.ReviewGradeMapper;
import com.ddis.ddis_hr.review.command.application.mapper.ReviewMapper;
import com.ddis.ddis_hr.review.command.application.mapper.ReviewedPerformanceMapper;
import com.ddis.ddis_hr.review.command.domain.aggregate.Review;
import com.ddis.ddis_hr.review.command.domain.aggregate.ReviewGrade;
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
    private final GoalsMapper goalsMapper;
    private final ReviewMapper reviewMapper;
    private final ReviewGradeMapper reviewGradeMapper;
    private final ReviewedPerformanceMapper reviewedPerformanceMapper;

    public ReviewServiceImpl(EmployeeReviewMapper employeeReviewMapper, ReviewRepository reviewRepository, EmployeeRepository employeeRepository,PerformanceRepository performanceRepository, GoalsMapper goalsMapper, ReviewMapper reviewMapper, ReviewGradeMapper reviewGradeMapper, ReviewedPerformanceMapper reviewedPerformanceMapper) {
        this.employeeReviewMapper = employeeReviewMapper;
        this.reviewRepository = reviewRepository;
        this.employeeRepository = employeeRepository;
        this.performanceRepository = performanceRepository;
        this.goalsMapper = goalsMapper;
        this.reviewMapper = reviewMapper;
        this.reviewGradeMapper = reviewGradeMapper;
        this.reviewedPerformanceMapper = reviewedPerformanceMapper;
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
    public List<ReviewedPerformanceDTO> getReviewedPerformances(Long reviewerId) {
        return reviewedPerformanceMapper.findReviewedPerformances(reviewerId);
    }
    @Override
    public PerformanceReviewDTO saveEvaluation(
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

        double achievementRate = 0;
        if (perf.getPerformanceValue() != null && perf.getGoal().getGoalValue() > 0) {
            achievementRate = perf.getPerformanceValue() * 100.0 / perf.getGoal().getGoalValue();
        }

// cappedRate: 100 초과하면 잘라내기
        double cappedRate = Math.min(achievementRate, 100);
        double weightFraction = perf.getGoal().getGoalWeight() / 100.0;
        double managerPercent = reviewerScore * 20.0;

        double baseScore = cappedRate * weightFraction * 0.7
                + managerPercent * 0.3;

// 초과 보너스 계산
        double rawExcess = Math.max(achievementRate - 100, 0);
        double MAX_EXCESS = 50;
        double effExcess = Math.min(rawExcess, MAX_EXCESS);

        double bonus;
        if (effExcess <= 10) {
            bonus = effExcess * 0.2;
        } else if (effExcess <= 30) {
            bonus = 10 * 0.2
                    + (effExcess - 10) * 0.3;
        } else {
            bonus = 10 * 0.2
                    + 20 * 0.3
                    + (effExcess - 30) * 0.5;
        }

        double finalScore = baseScore + bonus;

        int scoreKey = (int) Math.floor(finalScore);
        ReviewGrade grade = reviewGradeMapper.selectByScore(scoreKey);
        if (grade == null) {
            throw new IllegalStateException("해당 점수 구간 등급 없음: " + scoreKey);
        }

        Review review = reviewMapper.selectBySelfreviewId(performanceId);
        if (review == null) {
            review = new Review();
        }
        review.setSelfreviewId(perf);
        review.setEmployeeId(reviewer);
        review.setReviewScore(finalScore);
        review.setReviewGradeId(grade);
        reviewRepository.save(review);


        // 8) JPA save → insert/update 자동 분기
        reviewRepository.save(review);

        return PerformanceReviewDTO.builder()
                .performanceId(perf.getId())             // getId() → getPerformanceId()
                .reviewerScore(perf.getReviewerScore())
                .reviewerContent(perf.getReviewerContent())
                .reviewerCreatedAt(perf.getReviewerCreatedAt())      // 날짜 필드도 추가
                .build();
    }
}
