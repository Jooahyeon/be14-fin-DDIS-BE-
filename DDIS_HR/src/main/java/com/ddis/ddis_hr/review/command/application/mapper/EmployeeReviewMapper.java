package com.ddis.ddis_hr.review.command.application.mapper;

import com.ddis.ddis_hr.review.command.application.dto.EmployeeReviewDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeReviewMapper {
    List<EmployeeReviewDTO> findEmployeeByTeamId(@Param("teamId") Long teamId);
}
