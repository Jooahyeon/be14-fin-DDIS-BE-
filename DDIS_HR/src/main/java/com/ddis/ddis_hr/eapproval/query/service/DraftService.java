package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.DraftDetailResponseQueryDTO;

public interface DraftService {
    DraftDetailResponseQueryDTO getDraftDetail(Long docId);
}

