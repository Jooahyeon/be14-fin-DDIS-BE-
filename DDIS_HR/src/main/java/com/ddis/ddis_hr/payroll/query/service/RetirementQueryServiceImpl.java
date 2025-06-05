package com.ddis.ddis_hr.payroll.query.service;

import com.ddis.ddis_hr.payroll.query.dao.RetirementMapper;
import com.ddis.ddis_hr.payroll.query.dto.RetirementDetailDTO;
import com.ddis.ddis_hr.payroll.query.dto.RetirementSearchCondition;
import com.ddis.ddis_hr.payroll.query.dto.RetirementSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetirementQueryServiceImpl implements RetirementQueryService {
    private final RetirementMapper retirementMapper;

    @Override
    public List<RetirementSummaryDTO> getSummaries(RetirementSearchCondition condition) {
        return retirementMapper.getSummaries(
                condition.isFilterByRetireDate(),
                condition.isFilterByProvisionDate(),
                condition.getFromDate(),
                condition.getToDate(),
                condition.getProvisionSituation(),
                condition.getKeyword()
        );
    }

    @Override
    public RetirementDetailDTO getDetail(Long employeeId) {
        return retirementMapper.getDetail(employeeId);
    }
}
