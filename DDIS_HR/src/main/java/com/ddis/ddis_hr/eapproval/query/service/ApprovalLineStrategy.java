package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.ApproverInfoQueryDTO;

import java.util.List;

public interface ApprovalLineStrategy {
    List<ApproverInfoQueryDTO> createApprovalLine(Long drafterId);

}
