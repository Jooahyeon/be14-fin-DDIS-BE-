package com.ddis.ddis_hr.review.command.application.controller;

import com.ddis.ddis_hr.review.command.application.dto.EmployeeReviewDTO;
import com.ddis.ddis_hr.review.command.application.dto.EvaluationRequest;
import com.ddis.ddis_hr.review.command.application.dto.ReviewHistoryDTO;
import com.ddis.ddis_hr.review.command.application.mapper.ReviewMapper;
import com.ddis.ddis_hr.review.command.application.service.ReviewService;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{teamId}")
    public List<EmployeeReviewDTO> getTeamReview(@PathVariable("teamId") Long teamId){
        return reviewService.getTeamReview(teamId);
    }
    @GetMapping("/history/{employeeId}")
    public ResponseEntity<List<ReviewHistoryDTO>> getHistoryByEmployeeId(@PathVariable("employeeId") Long employeeId){
        List<ReviewHistoryDTO> historyList = reviewMapper.selectHistoryByEmployeeId(employeeId);
        return ResponseEntity.ok(historyList);
    }

    @PostMapping("/manager/performance/{performanceId}/evaluate")
    public ResponseEntity<Void> managerEvaluate(
            @PathVariable Long performanceId,
            @RequestBody EvaluationRequest request
    ) {
        reviewService.saveEvaluation(
                performanceId,
                request.getEmployeeIdReviewer(),
                request.getReviewerScore(),
                request.getReviewerContent(),
                request.getDecision()        // "승인" or "반려" 같은 상태
        );
        return ResponseEntity.ok().build();
    }


}
