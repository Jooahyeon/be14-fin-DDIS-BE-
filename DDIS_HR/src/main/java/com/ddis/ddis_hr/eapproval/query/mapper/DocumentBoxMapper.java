package com.ddis.ddis_hr.eapproval.query.mapper;

import com.ddis.ddis_hr.eapproval.query.dto.DocumentDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDTO;
import com.ddis.ddis_hr.eapproval.query.dto.ReferenceDocDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DocumentBoxMapper {
        // 1. 기안함
        List<DraftDTO> selectDocumentsByDrafter(@Param("employeeId") Long employeeId);

        // 2. 결재함
        List<DocumentDTO> selectPendingDocuments(@Param("employeeId") Long employeeId);   // 결재
        List<DocumentDTO> selectInProgressDocuments(@Param("employeeId") Long employeeId); // 진행
        List<DocumentDTO> selectCompletedDocuments(@Param("employeeId") Long employeeId);  // 완료
        List<DocumentDTO> selectAllDocuments(@Param("employeeId") Long employeeId);        // 전체

        // 참조함 조회
        List<ReferenceDocDTO> selectReferenceDocsByEmployeeId(@Param("employeeId") Long employeeId);

        // 읽음 여부 추가
        void updateReadStatus(@Param("employeeId") Long employeeId,
                              @Param("docId") Long docId,
                              @Param("readAt") LocalDateTime readAt);

        // 수신함 조회
        List<ReferenceDocDTO> selectReceiverDocsByEmployeeId(Long employeeId);


        List<DraftDTO> selectAllDrafts(Long employeeId);
}
