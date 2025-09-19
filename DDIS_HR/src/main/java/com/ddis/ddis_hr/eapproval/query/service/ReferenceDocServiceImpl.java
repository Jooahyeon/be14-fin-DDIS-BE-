package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.ReferenceDocDTO;
import com.ddis.ddis_hr.eapproval.query.mapper.DocumentBoxMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ReferenceDocServiceImpl implements ReferenceDocService{
    private final DocumentBoxMapper documentBoxMapper;

    public ReferenceDocServiceImpl(DocumentBoxMapper documentBoxMapper) {
        this.documentBoxMapper = documentBoxMapper;
    }

    public List<ReferenceDocDTO> getReferenceDocsByEmployeeId(Long employeeId) {
        return documentBoxMapper.selectReferenceDocsByEmployeeId(employeeId);
    }

    @Override
    public void markAsRead(Long employeeId, Long docId) {
        documentBoxMapper.updateReadStatus(employeeId, docId, LocalDateTime.now());
    }
}
