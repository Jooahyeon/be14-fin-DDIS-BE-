package com.ddis.ddis_hr.review.command.application.mapper;

import com.ddis.ddis_hr.review.command.application.dto.ReviewHistoryDTO;
import com.ddis.ddis_hr.review.command.domain.aggregate.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {
    Review selectBySelfreviewId(@Param("perfId") Long performanceId);

    List<ReviewHistoryDTO> selectHistoryByEmployeeId(@Param("employeeId") Long employeeId);
}
