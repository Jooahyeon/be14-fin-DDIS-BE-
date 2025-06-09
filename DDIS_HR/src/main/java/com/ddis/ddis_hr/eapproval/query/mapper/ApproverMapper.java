package com.ddis.ddis_hr.eapproval.query.mapper;

import com.ddis.ddis_hr.eapproval.query.dto.ApproverInfoQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**
 * ApproverMapper
 *
 * 조직 기준으로 실제 결재자를 조회하는 MyBatis 매퍼 인터페이스
 */
@Mapper
public interface ApproverMapper {

    /**
     * findApproverByPositionAndOrg
     *
     * @param positionName     결재 단계에서 요구하는 직급명 (예: "팀장", "부서장", "본부장")
     * @param organizationType 조직 단위 구분자
     *                         - "team"       : 팀 단위로 조회
     *                         - "department" : 부서 단위로 조회
     *                         - "head"       : 본부 단위로 조회
     * @param organizationId   조회 대상 조직 ID (teamId, departmentId, headId 중 하나)
     * @return 단일 EmployeeSimpleDTO
     *         - 조회된 결재자가 없으면 null 반환
     */
    ApproverInfoQueryDTO findApproverByPositionAndOrg(
            @Param("positionName")    String positionName,
            @Param("organizationType") String organizationType,
            @Param("organizationId")   Long organizationId
    );
}