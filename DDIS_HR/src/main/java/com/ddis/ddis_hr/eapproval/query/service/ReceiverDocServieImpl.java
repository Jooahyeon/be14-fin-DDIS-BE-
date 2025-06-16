package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.ReferenceDocDTO;
import com.ddis.ddis_hr.eapproval.query.mapper.DocumentBoxMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiverDocServieImpl implements ReceiverDocService{

    private final DocumentBoxMapper documentBoxMapper;

    @Override
    public List<ReferenceDocDTO> getReceiverDocsByEmployeeId(Long employeeId) {
        return documentBoxMapper.selectReceiverDocsByEmployeeId(employeeId);
    }
}
