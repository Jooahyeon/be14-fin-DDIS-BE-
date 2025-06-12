package com.ddis.ddis_hr.review.query.controller;

import com.ddis.ddis_hr.review.query.dto.PerformanceHistoryDTO;
import com.ddis.ddis_hr.review.query.service.PerformanceHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review/history")
public class PerformanceReviewQueryController {

    private final PerformanceHistoryService performanceHistoryService;

    @GetMapping("/all")
    public ResponseEntity<List<PerformanceHistoryDTO>> getAllPerformanceHistory() {
        List<PerformanceHistoryDTO> histories = performanceHistoryService.getAllPerformanceHistory();
        return ResponseEntity.ok(histories);
    }
}
