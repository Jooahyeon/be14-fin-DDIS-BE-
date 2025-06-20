package com.ddis.ddis_hr.review.query.service;

import com.ddis.ddis_hr.review.command.application.mapper.PerformanceHistoryMapper;
import com.ddis.ddis_hr.review.query.dto.PerformanceHistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerformanceHistoryServiceImpl implements PerformanceHistoryService {
    private final PerformanceHistoryMapper performanceHistoryMapper;

    @Override
    public List<PerformanceHistoryDTO> getAllPerformanceHistory() {
        return performanceHistoryMapper.findAllPerformanceHistories();
    }

    @Override
    public List<PerformanceHistoryDTO> getPerformanceHistoriesByEmployee(Long employeeId) {
        return performanceHistoryMapper.findPerformanceHistoriesByEmployee(employeeId);
    }
}
