package com.ddis.ddis_hr.eapproval.query.dto;

import lombok.*;

/**
 * 자동/수동 결재선 구성 시 결재자 정보를 담는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApproverInfoQueryDTO {

    /** 결재자 사번 */
    private Long employeeId;

    /** 직위(포지션) ID */
    private Long positionId;

    /** 결재자 이름 */
    private String employeeName;

    /** 결재자 소속 팀명 */
    private String teamName;

    /** 결재자의 직위명 (예: 팀장, 부서장 등) */
    private String positionName;

    /** 결재자 소속 부서명 */
    private String departmentName;

    /** 결재 유형 코드 (예: 승인, 협조 등) */
    private String type;

    /** 결재선 유형 코드 (예: 자동, 수동 등) */
    private String lineType;

    /** 결재 유형 한글 라벨 */
    private String typeLabel;

    /** 결재선 유형 한글 라벨 */
//    private String lineTypeLabel;

    /** 결재선 단계 */
    private int step;

    // ↓ 여기를 추가 ↓

    /** 직급 ID */
    private Long rankId;

    /** 직급명 */
    private String rankName;

    /** 직급 정렬 순서 */
    private int rankOrder;

    /** 역할 */
    private int role;
}
