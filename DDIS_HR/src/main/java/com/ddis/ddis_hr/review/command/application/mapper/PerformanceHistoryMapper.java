package com.ddis.ddis_hr.review.command.application.mapper;

import com.ddis.ddis_hr.review.query.dto.PerformanceHistoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PerformanceHistoryMapper {
    List<PerformanceHistoryDTO> findAllPerformanceHistories();
}
