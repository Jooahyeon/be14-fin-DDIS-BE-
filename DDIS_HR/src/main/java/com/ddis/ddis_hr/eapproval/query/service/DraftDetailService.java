package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.DraftDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDetailResponseQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.FindDrafterQueryDTO;

import java.util.List;

public interface DraftDetailService {
    DraftDetailResponseQueryDTO getDraftDetail(Long docId);

    List<DraftDTO> getMyDrafts(Long employeeId);

    // 회수
    void recallDocument(Long docId);

    FindDrafterQueryDTO getfindDrafterInfo(Long employeeId);

}

