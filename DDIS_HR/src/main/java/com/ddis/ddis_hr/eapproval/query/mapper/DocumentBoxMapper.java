package com.ddis.ddis_hr.eapproval.query.mapper;

import com.ddis.ddis_hr.eapproval.query.dto.ReferenceDocDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDateTime;
import java.util.List;

// 수신/참조 조회용



@Mapper
public interface DocumentBoxMapper {
    // 참조함 조회
    List<ReferenceDocDTO> selectReferenceDocsByEmployeeId(@Param("employeeId") Long employeeId);

    // 읽음 여부 추가
    void updateReadStatus(@Param("employeeId") Long employeeId,
                          @Param("docId") Long docId,
                          @Param("readAt") LocalDateTime readAt);

    // 수신함 조회
    List<ReferenceDocDTO> selectReceiverDocsByEmployeeId(Long employeeId);

}