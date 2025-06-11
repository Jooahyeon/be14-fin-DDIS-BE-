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
     * 포지션명 + 조직 구분(팀/부서/본부) + 조직 ID 로 결재자 1명을 찾는다.
     *
     * @param positionName     직책(예: "팀장")
     * @param organizationType 조직 구분 (team|department|head)
     * @param organizationId   해당 조직 PK
     */
    ApproverInfoQueryDTO findApproverByPositionAndOrg(@Param("positionName") String positionName,
                                                      @Param("organizationType") String organizationType,
                                                      @Param("organizationId") Long organizationId);
}