package com.ddis.ddis_hr.review.command.application.controller;

import com.ddis.ddis_hr.review.command.application.dto.EmployeeReviewDTO;
import com.ddis.ddis_hr.review.command.application.service.ReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{teamId}")
    public List<EmployeeReviewDTO> getTeamReview(@PathVariable("teamId") Long teamId){
        return reviewService.getTeamReview(teamId);
    }


}
