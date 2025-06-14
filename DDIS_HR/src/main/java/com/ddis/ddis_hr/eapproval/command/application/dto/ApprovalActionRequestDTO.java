package com.ddis.ddis_hr.eapproval.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 승인/반려 시 요청으로 넘겨받을 데이터
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalActionRequestDTO{

    /**
     * 결재자가 입력한 의견 (Optional)
     */
    private String opinion;
}