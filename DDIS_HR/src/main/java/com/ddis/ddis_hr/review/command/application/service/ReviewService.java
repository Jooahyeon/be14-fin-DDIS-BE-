package com.ddis.ddis_hr.review.command.application.service;

import com.ddis.ddis_hr.goals.command.application.dto.PerformanceDTO;
import com.ddis.ddis_hr.goals.command.application.dto.PerformanceResponseDTO;
import com.ddis.ddis_hr.goals.command.application.dto.PerformanceReviewDTO;
import com.ddis.ddis_hr.review.command.application.dto.EmployeeReviewDTO;
import com.ddis.ddis_hr.review.command.application.dto.ReviewedPerformanceDTO;

import java.util.List;

public interface ReviewService {
    List<EmployeeReviewDTO> getTeamReview(Long teamCode);

    PerformanceReviewDTO saveEvaluation(
            Long performanceId,
            Long employeeIdReviewer,
            Integer reviewerScore,
            String reviewerContent,
            String decision
    );
    List<ReviewedPerformanceDTO> getReviewedPerformances(Long reviewerId);
}
