package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.ReferenceDocDTO;
import com.ddis.ddis_hr.eapproval.query.mapper.DocumentBoxMapper;

import java.util.List;

public interface ReceiverDocService {

    List<ReferenceDocDTO> getReceiverDocsByEmployeeId(Long employeeId);
}
