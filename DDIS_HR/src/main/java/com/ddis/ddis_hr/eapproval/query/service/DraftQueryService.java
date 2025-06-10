package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateCommandDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDetailResponseQueryDTO;

public interface DraftQueryService {
    DraftDetailResponseQueryDTO getDraftDetail(Long docId);

    Long createDraft(DraftCreateCommandDTO requestDto);
}

