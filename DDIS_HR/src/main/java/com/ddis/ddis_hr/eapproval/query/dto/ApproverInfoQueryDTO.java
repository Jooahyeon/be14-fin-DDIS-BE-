package com.ddis.ddis_hr.eapproval.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ApproverInfoDTO
 *
 * 단일 결재자의 최소 정보만 담는 조회용 DTO
 * - employeeId: 실제 결재자 사번
 * - positionId: 결재자 포지션 ID
 * - employeeName: 결재자 이름
 * - teamName: 결재자 팀 이름
 * - departmentName: 결재자 부서 이름
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApproverInfoQueryDTO {

    private Long employeeId;     // 실제 결재자 사번
    private Long positionId;     // 포지션 ID
    private String employeeName; // 이름
    private String teamName;
    private String departmentName;
}
