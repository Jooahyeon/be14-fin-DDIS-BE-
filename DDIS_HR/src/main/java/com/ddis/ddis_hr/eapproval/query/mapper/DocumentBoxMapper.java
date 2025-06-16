package com.ddis.ddis_hr.eapproval.query.mapper;

import com.ddis.ddis_hr.eapproval.query.dto.ReferenceDocDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DocumentBoxMapper {
    List<ReferenceDocDTO> selectReferenceDocsByEmployeeId(@Param("employeeId") Long employeeId);

    void updateReadStatus(@Param("employeeId") Long employeeId,
                          @Param("docId") Long docId,
                          @Param("readAt") LocalDateTime readAt);

    List<ReferenceDocDTO> selectReceiverDocsByEmployeeId(Long employeeId);
}
