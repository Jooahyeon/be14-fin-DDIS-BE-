package com.ddis.ddis_hr.review.command.application.controller;

import com.ddis.ddis_hr.goals.command.application.dto.PerformanceDTO;
import com.ddis.ddis_hr.goals.command.application.dto.PerformanceResponseDTO;
import com.ddis.ddis_hr.goals.command.application.dto.PerformanceReviewDTO;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import com.ddis.ddis_hr.review.command.application.dto.EmployeeReviewDTO;
import com.ddis.ddis_hr.review.command.application.dto.EvaluationRequest;
import com.ddis.ddis_hr.review.command.application.dto.ReviewHistoryDTO;
import com.ddis.ddis_hr.review.command.application.dto.ReviewedPerformanceDTO;
import com.ddis.ddis_hr.review.command.application.mapper.ReviewMapper;
import com.ddis.ddis_hr.review.command.application.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    public ReviewController(ReviewService reviewService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @PreAuthorize("hasRole('ROLE_TEAMLEADER')")
    @GetMapping("/{teamId}")
    public List<EmployeeReviewDTO> getTeamReview(@PathVariable("teamId") Long teamId){
        return reviewService.getTeamReview(teamId);
    }
    @GetMapping("/history/{employeeId}")
    public ResponseEntity<List<ReviewHistoryDTO>> getHistoryByEmployeeId(@PathVariable("employeeId") Long employeeId){
        List<ReviewHistoryDTO> historyList = reviewMapper.selectHistoryByEmployeeId(employeeId);
        System.out.println("▶ history size = " + historyList.size());
        return ResponseEntity.ok(historyList);
    }

    @PostMapping("/manager/performance/{performanceId}/evaluate")
    public ResponseEntity<PerformanceReviewDTO> managerEvaluate(
            @PathVariable("performanceId") Long performanceId,
            @RequestBody EvaluationRequest request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long reviewerId = user.getEmployeeId();
        PerformanceReviewDTO updated = reviewService.saveEvaluation(
                performanceId,
                reviewerId,
                request.getReviewerScore(),
                request.getReviewerContent(),
                request.getDecision()        // "승인" or "반려" 같은 상태
        );
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{reviewerId}/performance")
    public ResponseEntity<List<ReviewedPerformanceDTO>> getMyReviews(
            @PathVariable Long reviewerId) {
        return ResponseEntity.ok(
                reviewService.getReviewedPerformances(reviewerId)
        );
    }


}
