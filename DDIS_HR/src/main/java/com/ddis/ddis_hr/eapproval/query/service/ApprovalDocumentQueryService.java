package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.DocumentDTO;

import java.util.List;

public interface ApprovalDocumentQueryService {
    List<DocumentDTO> getPendingApprovals(Long employeeId);
}
