package com.ddis.ddis_hr.payroll.query.dao;

import com.ddis.ddis_hr.payroll.query.dto.SalaryDetailDTO;
import com.ddis.ddis_hr.payroll.query.dto.SalarySearchCondition;
import com.ddis.ddis_hr.payroll.query.dto.SalarySummaryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface SalaryMapper {
    List<SalarySummaryDTO> searchSalarySummaries(SalarySearchCondition condition);
    SalaryDetailDTO findSalaryDetail(@Param("employeeId") Long employeeId,
                                     @Param("month") String month); // '2025-05' 포맷

}