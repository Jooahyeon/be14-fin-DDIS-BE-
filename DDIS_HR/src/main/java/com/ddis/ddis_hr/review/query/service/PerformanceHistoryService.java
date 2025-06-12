package com.ddis.ddis_hr.review.query.service;

import com.ddis.ddis_hr.review.query.dto.PerformanceHistoryDTO;

import java.util.List;

public interface PerformanceHistoryService {
    List<PerformanceHistoryDTO> getAllPerformanceHistory();
}
