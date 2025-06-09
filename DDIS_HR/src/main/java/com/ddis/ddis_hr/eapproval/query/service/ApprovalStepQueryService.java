package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.ApprovalStepQueryDTO;

import java.util.List;

public interface ApprovalStepQueryService {
    List<ApprovalStepQueryDTO> findSteps(Long positionId);

}
