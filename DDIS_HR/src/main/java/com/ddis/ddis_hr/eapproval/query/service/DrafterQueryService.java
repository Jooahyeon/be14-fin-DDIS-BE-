package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.FindDrafterQueryDTO;

public interface DrafterQueryService {
    FindDrafterQueryDTO getfindDrafterInfo(Long employeeId);

}
