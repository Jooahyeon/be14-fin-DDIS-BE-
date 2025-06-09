package com.ddis.ddis_hr.eapproval.query.dto;

import lombok.*;

//로그인한 사용자의 조직 정보를 조회할 때 사용 (단순 조회용)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

/**
 * FindDrafterDTO
 *
 * JWT 토큰이나 세션에서 추출한 로그인한 사용자의
 * 기본 정보(사번·이름·조직구조)를 담는 DTO
 */
public class FindDrafterQueryDTO {
    /** 로그인한 사용자의 사번(empId) */
    private String empId;

    /** 로그인한 사용자의 이름 */
    private String name;

    /** 로그인한 사용자의 직급명 (예: "팀원", "팀장") */
    private String position;

    /** 로그인한 사용자가 속한 팀 이름 */
    private String team;

    /** 로그인한 사용자가 속한 부서 이름 */
    private String department;

    /** 로그인한 사용자가 속한 본부 이름 */
    private String head;

    /** 로그인한 사용자의 직급 ID (ApprovalStep 조회용) */
    private Long positionId;

    /** 팀 기준 결재자 매칭용 팀 ID */
    private Long teamId;

    /** 부서 기준 결재자 매칭용 부서 ID */
    private Long departmentId;

    /** 본부 기준 결재자 매칭용 본부 ID */
    private Long headId;

    /**
     * positionId 필드를 그대로 반환
     * - 서비스 레이어에서 단계별 승인 직급 조회 시 사용
     */
    public Long getPositionId() {
        return this.positionId;
    }
}
