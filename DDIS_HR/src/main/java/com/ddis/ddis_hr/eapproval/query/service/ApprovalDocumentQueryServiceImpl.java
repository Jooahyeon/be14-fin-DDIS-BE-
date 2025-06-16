package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.DocumentDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDTO;
import com.ddis.ddis_hr.eapproval.query.mapper.ApprovalDocumentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApprovalDocumentQueryServiceImpl implements ApprovalDocumentQueryService {

    private final ApprovalDocumentMapper documentMapper;

    @Override
    public List<DocumentDTO> getPendingApprovals(Long employeeId) {
        return documentMapper.selectDocumentsByStatus(employeeId, "심사중", "미결");
    }

    @Override
    public List<DocumentDTO> getInProgressApprovals(Long employeeId) {
        return documentMapper.selectDocumentsByStatus(employeeId, "심사중", "승인");
    }

    @Override
    public List<DocumentDTO> getCompletedApprovals(Long employeeId) {
        return documentMapper.selectDocumentsByStatus(employeeId, "결재완료", "승인");
    }

    @Override
    public List<DocumentDTO> getAllApprovals(Long employeeId) {
        return documentMapper.selectAllDocuments(employeeId); // 반려 포함 전체
    }

    // 기안함
    @Override
    public List<DraftDTO> getMyDrafts(Long employeeId) {
        return documentMapper.selectDocumentsByDrafter(employeeId);
    }
}
