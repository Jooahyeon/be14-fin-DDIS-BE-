package com.ddis.ddis_hr.payroll.query.service;

import com.ddis.ddis_hr.payroll.query.dto.RetirementDetailDTO;
import com.ddis.ddis_hr.payroll.query.dto.RetirementSearchCondition;
import com.ddis.ddis_hr.payroll.query.dto.RetirementSummaryDTO;

import java.util.List;

public interface RetirementQueryService {
    List<RetirementSummaryDTO> getSummaries(RetirementSearchCondition condition);
    RetirementDetailDTO getDetail(Long employeeId);
}
