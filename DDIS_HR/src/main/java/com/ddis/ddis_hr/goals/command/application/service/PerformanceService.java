package com.ddis.ddis_hr.goals.command.application.service;

import com.ddis.ddis_hr.goals.command.application.dto.PerformanceRequestDTO;
import com.ddis.ddis_hr.goals.command.application.dto.PerformanceResponseDTO;

public interface PerformanceService {
     PerformanceResponseDTO savePerformance(
            Long goalId,
            PerformanceRequestDTO info,
            Long employeeId
    );

    PerformanceResponseDTO updatePerformance(
            Long goalId,
            Long perfId,
            PerformanceRequestDTO info,
            Long employeeId
    );
    void deletePerformance(Long goalId, Long perfId, Long employeeId);
}
