package com.ddis.ddis_hr.eapproval.query.mapper;

import com.ddis.ddis_hr.eapproval.query.dto.DocumentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApprovalDocumentMapper {
    List<DocumentDTO> selectDocumentsByStatus(
            @Param("employeeId") Long employeeId,
            @Param("docStatus") String docStatus,
            @Param("lineStatus") String lineStatus
    );

    List<DocumentDTO> selectAllDocuments(@Param("employeeId") Long employeeId);}
