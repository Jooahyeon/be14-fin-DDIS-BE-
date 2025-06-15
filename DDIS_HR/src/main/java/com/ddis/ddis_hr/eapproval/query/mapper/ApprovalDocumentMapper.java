package com.ddis.ddis_hr.eapproval.query.mapper;

import com.ddis.ddis_hr.eapproval.query.dto.DocumentDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApprovalDocumentMapper {
    // 결재함
    List<DocumentDTO> selectDocumentsByStatus(
            @Param("employeeId") Long employeeId,
            @Param("docStatus") String docStatus,
            @Param("lineStatus") String lineStatus
    );

    List<DocumentDTO> selectAllDocuments(@Param("employeeId") Long employeeId);


    // 기안함
    List<DraftDTO> selectDocumentsByDrafter(@Param("employeeId") Long employeeId);

}
