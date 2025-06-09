package com.ddis.ddis_hr.eapproval.query.dto;

// 결재라인 생성 시 단계별 포지션 규칙을 활용해 로직을 조립하는 중간 DTO

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ✅ 결재라인 자동 생성 시 사용하는 DTO
 * - position(예: 팀원, 팀장)에 따라 결재 단계를 지정하기 위한 DTO
 * - MyBatis 쿼리 결과를 매핑하는 데 사용
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalStepQueryDTO {

    private int step;              // 결재 순서 (1, 2, 3 ...)
    private String positionName;  // 해당 단계에서 필요한 포지션명 (예: 팀장, 부서장)

}
