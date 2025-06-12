package com.ddis.ddis_hr.eapproval.query.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 각 승인 단계별 실제 결재자 정보를 담는 DTO
 */

@Getter
@Setter
@AllArgsConstructor
public class ApproverQueryDTO {
    private int stepNo;
    private List<ApproverInfoQueryDTO> approverList;
}
