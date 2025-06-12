package com.ddis.ddis_hr.review.command.application.mapper;

import com.ddis.ddis_hr.review.command.application.dto.ReviewedPerformanceDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewedPerformanceMapper {
    List<ReviewedPerformanceDTO> findReviewedPerformances(
            @Param("reviewerId") Long reviewerId
    );
}
