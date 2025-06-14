package com.ddis.ddis_hr.eapproval.command.application.service;

import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateCommandDTO;
import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateResponseCommandDTO;
import com.ddis.ddis_hr.eapproval.command.domain.entity.Draft;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDetailResponseQueryDTO;
import jakarta.transaction.Transactional;

// 기안문 작성·수정·조회 로직 서비스
public interface DraftCommandService {
    /**
         * 새로운 기안문 생성
         * @param commandDto 사용자 요청 DTO
         * @return 생성된 문서 PK (docId)
         */

//        DraftCreateResponseCommandDTO createDraft(DraftCreateCommandDTO commandDto);


    @Transactional
    DraftCreateResponseCommandDTO createDraft(DraftCreateCommandDTO dto);

    /**
         * 기안문 및 결재라인을 저장한 뒤, Draft 엔터티 반환
         * @param dto 기안 요청 DTO
         * @return 저장된 Draft 엔터티
         */
        Draft saveDraftAndLines(DraftCreateCommandDTO dto); // 새로 추가


        /**
         * 기안문 상세 조회
         * @param docId 문서 PK
         * @return 화면 렌더링용 상세 DTO
         */
//        DraftDetailResponseQueryDTO getDraftDetail(Long docId);



}