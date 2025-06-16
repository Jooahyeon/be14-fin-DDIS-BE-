package com.ddis.ddis_hr.eapproval.query.dto;

import lombok.*;

/**
 * FindDrafterQueryDTO
 *
 * 로그인한 기안자의 기본 정보(조직 및 직급 포함)를 담는 DTO
 */

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FindDrafterQueryDTO {
    /**
     * 로그인한 사용자의 사번
     */
    private Long empId;

    /**
     * 로그인한 사용자의 이름
     */
    private String name;

    /**
     * 로그인한 사용자의 직급명
     */
    private String rankName;

    /**
     * 로그인한 사용자의 직급 ID
     */
    private Long rankId;

    /**
     * 로그인한 사용자의 직급 정렬값
     */
    private int rankOrder;

    /**
     * 팀 ID
     */
    private Long teamId;

    /**
     * 팀 이름
     */
    private String teamName;

    /**
     * 부서 ID
     */
    private Long departmentId;

    /**
     * 부서 이름
     */
    private String departmentName;

    /**
     * 본부 ID
     */
    private Long headId;

    /**
     * 본부 이름
     */
    private String headName;

}