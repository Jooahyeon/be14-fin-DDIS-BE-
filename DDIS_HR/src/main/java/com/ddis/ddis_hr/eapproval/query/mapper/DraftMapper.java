package com.ddis.ddis_hr.eapproval.query.mapper;

import com.ddis.ddis_hr.eapproval.query.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


// 기안문 회수
@Mapper
public interface DraftMapper {

        // ① 기안문 상세 조회
        DraftDetailResponseQueryDTO selectDraftDetail(@Param("docId") Long docId);

        // ② 1차 결재자(step=2) 결재 여부 체크
        int countFirstApproverAction(@Param("docId") Long docId);

        // ③ document_box에서 기안자 제외한 결재자·협조자 엔트리 삭제
        void deleteDocumentBoxExceptDrafter(@Param("docId") Long docId);

        // ④ draft_documents 상태를 '회수'로 변경
        void updateDocumentStatusToRecalled(@Param("docId") Long docId);


}