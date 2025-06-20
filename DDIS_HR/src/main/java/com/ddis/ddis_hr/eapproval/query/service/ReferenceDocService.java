package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.ReferenceDocDTO;

import java.util.List;

public interface ReferenceDocService {

    List<ReferenceDocDTO> getReferenceDocsByEmployeeId(Long employeeId);

    void markAsRead(Long employeeId, Long docId);
}
