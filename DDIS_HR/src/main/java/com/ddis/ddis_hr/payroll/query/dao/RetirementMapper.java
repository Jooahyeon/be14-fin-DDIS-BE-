package com.ddis.ddis_hr.payroll.query.dao;

import com.ddis.ddis_hr.payroll.query.dto.RetirementDetailDTO;
import com.ddis.ddis_hr.payroll.query.dto.RetirementSearchCondition;
import com.ddis.ddis_hr.payroll.query.dto.RetirementSummaryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface RetirementMapper {
    // 퇴직금 목록 조회
    List<RetirementSummaryDTO> getSummaries(
            @Param("filterByRetireDate") boolean filterByRetireDate,
            @Param("filterByProvisionDate") boolean filterByProvisionDate,
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("provisionSituation") String provisionSituation,
            @Param("keyword") String keyword
    );

    // 퇴직금 상세 조회
    RetirementDetailDTO getDetail(@Param("employeeId") Long employeeId);
}
