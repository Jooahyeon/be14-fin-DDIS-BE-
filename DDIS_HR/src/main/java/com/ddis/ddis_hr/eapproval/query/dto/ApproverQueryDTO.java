package com.ddis.ddis_hr.eapproval.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

/**
 * 각 승인 단계별 실제 결재자 정보를 담는 DTO
 */
@Data
@AllArgsConstructor
public class ApproverQueryDTO {
    private int step;                    // 승인 라인 순서
    private List<ApproverInfoQueryDTO> approvers; // 해당 단계에 매칭된 결재자 리스트
}
