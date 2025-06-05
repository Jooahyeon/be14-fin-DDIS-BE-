package com.ddis.ddis_hr.review.command.application.service;

import com.ddis.ddis_hr.review.command.application.dto.EmployeeReviewDTO;

import java.util.List;

public interface ReviewService {
    List<EmployeeReviewDTO> getTeamReview(Long teamCode);
}
