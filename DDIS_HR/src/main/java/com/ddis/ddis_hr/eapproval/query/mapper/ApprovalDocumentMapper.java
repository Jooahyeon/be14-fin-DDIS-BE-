package com.ddis.ddis_hr.eapproval.query.mapper;

import com.ddis.ddis_hr.eapproval.query.dto.DocumentDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApprovalDocumentMapper {

        List<DocumentDTO> selectPendingDocuments(@Param("employeeId") Long employeeId);   // 결재
        List<DocumentDTO> selectInProgressDocuments(@Param("employeeId") Long employeeId); // 진행
        List<DocumentDTO> selectCompletedDocuments(@Param("employeeId") Long employeeId);  // 완료
        List<DocumentDTO> selectAllDocuments(@Param("employeeId") Long employeeId);        // 전체


        // 기안함
        List<DraftDTO> selectDocumentsByDrafter(@Param("employeeId") Long employeeId);


        List<DraftDTO> selectAllDrafts(Long employeeId);
}
