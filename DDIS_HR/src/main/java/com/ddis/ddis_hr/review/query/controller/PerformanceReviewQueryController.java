package com.ddis.ddis_hr.review.query.controller;

import com.ddis.ddis_hr.member.security.CustomUserDetails;
import com.ddis.ddis_hr.review.query.dto.PerformanceHistoryDTO;
import com.ddis.ddis_hr.review.query.service.PerformanceHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review/history")
public class PerformanceReviewQueryController {

    private final PerformanceHistoryService performanceHistoryService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_HR')")
    public ResponseEntity<List<PerformanceHistoryDTO>> getAllPerformanceHistory() {
        List<PerformanceHistoryDTO> histories = performanceHistoryService.getAllPerformanceHistory();
        return ResponseEntity.ok(histories);
    }
    @GetMapping("/employee")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PerformanceHistoryDTO>>
    getPerformanceHistoryByEmployeeId( @AuthenticationPrincipal CustomUserDetails principal) {
        Long employeeId = principal.getEmployeeId();
        return ResponseEntity.ok(
                performanceHistoryService.getPerformanceHistoriesByEmployee(employeeId)
        );
    }
}
