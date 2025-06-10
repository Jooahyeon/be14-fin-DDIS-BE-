package com.ddis.ddis_hr.eapproval.command.application.service;

import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateCommandDTO;

public interface DraftCommandService {



        /**
         * 새로운 기안문 생성
         * @param commandDto 사용자 요청 DTO
         * @return 생성된 문서 PK (docId)
         */
        Long createDraft(DraftCreateCommandDTO commandDto);

        /**
         * 기안문 상세 조회
         * @param docId 문서 PK
         * @return 화면 렌더링용 상세 DTO
         */
//        DraftDetailResponseQueryDTO getDraftDetail(Long docId);


}