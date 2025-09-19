package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.DocumentDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDTO;
import com.ddis.ddis_hr.eapproval.query.mapper.DocumentBoxMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApprovalDocumentQueryServiceImpl implements ApprovalDocumentQueryService {

    private final DocumentBoxMapper documentBoxMapper;

    // 기안함
    @Override
    public List<DraftDTO> getMyDrafts(Long employeeId) {
        return documentBoxMapper.selectDocumentsByDrafter(employeeId);
    }

    @Override
    public List<DocumentDTO> getPendingApprovals(Long employeeId) {
        return documentBoxMapper.selectPendingDocuments(employeeId);
    }

    @Override
    public List<DocumentDTO> getInProgressApprovals(Long employeeId) {
        return documentBoxMapper.selectInProgressDocuments(employeeId);
    }

    // 결재 완료
    @Override
    public List<DocumentDTO> getCompletedApprovals(Long employeeId) {
        return documentBoxMapper.selectCompletedDocuments(employeeId);
    }

    @Override
    public List<DocumentDTO> getAllApprovals(Long employeeId) {
        return documentBoxMapper.selectAllDocuments(employeeId);
    }


}