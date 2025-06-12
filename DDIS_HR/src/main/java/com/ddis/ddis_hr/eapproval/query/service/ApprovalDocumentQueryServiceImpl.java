package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.DocumentDTO;
import com.ddis.ddis_hr.eapproval.query.mapper.ApprovalDocumentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApprovalDocumentQueryServiceImpl implements ApprovalDocumentQueryService {

    private final ApprovalDocumentMapper documentMapper;


    public List<DocumentDTO> getPendingApprovals(Long employeeId) {
        return documentMapper.selectDocumentsByApprover(employeeId);
    }
}
