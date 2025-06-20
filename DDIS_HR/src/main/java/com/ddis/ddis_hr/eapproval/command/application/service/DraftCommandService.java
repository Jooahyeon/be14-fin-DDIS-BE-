package com.ddis.ddis_hr.eapproval.command.application.service;

import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateCommandDTO;
import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateResponseCommandDTO;
import com.ddis.ddis_hr.eapproval.command.domain.entity.DraftDocument;
import jakarta.transaction.Transactional;

// 기안문 작성·수정·조회 로직 서비스
public interface DraftCommandService {

    @Transactional
    DraftCreateResponseCommandDTO createDraft(DraftCreateCommandDTO dto);

    /**
         * 기안문 및 결재라인을 저장한 뒤, Draft 엔터티 반환
         * @param dto 기안 요청 DTO
         * @return 저장된 Draft 엔터티
         */
        DraftDocument saveDraftAndLines(DraftCreateCommandDTO dto); // 새로 추가



}