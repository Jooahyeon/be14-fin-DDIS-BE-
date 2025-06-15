package com.ddis.ddis_hr.eapproval.query.mapper;

import com.ddis.ddis_hr.eapproval.query.dto.DraftDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DraftDocumentMapper {
    List<DraftDTO> selectDraftsByDrafter(@Param("employeeId") Long employeeId);

    List<DraftDTO> selectDocumentsByDrafter(@Param("employeeId") Long employeeId);
}
