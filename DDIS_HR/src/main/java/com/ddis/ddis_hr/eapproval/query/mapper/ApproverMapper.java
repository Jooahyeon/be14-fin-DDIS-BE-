package com.ddis.ddis_hr.eapproval.query.mapper;

import com.ddis.ddis_hr.eapproval.query.dto.ApproverInfoQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * ApproverMapper
 *
 * 조직 기준으로 실제 결재자를 조회하는 MyBatis 매퍼 인터페이스
 */
@Mapper
public interface ApproverMapper {

    /**
     * 기안자 팀 상위 직급자 조회 (일반 기안문 등)
     */
    ApproverInfoQueryDTO findApproverByRankOrderAndOrg(
            @Param("rankOrder") int rankOrder,
            @Param("orgType")   String orgType,
            @Param("orgId")     Long orgId,
            @Param("step")      int step,
            @Param("type")      String type,
            @Param("lineType")  String lineType
    );

    /**
     * 특정 팀에서 가장 낮은 직급자를 조회 (예: 인사팀 일반 직원)
     */
    ApproverInfoQueryDTO findLowestRankInTeam(
            @Param("teamId")   Long teamId,
            @Param("type")     String type,
            @Param("lineType") String lineType
    );

    /**
     * 특정 팀에서 가장 높은 직급자를 조회 (예: 인사팀 팀장)
     */
    ApproverInfoQueryDTO findHighestRankInTeam(
            @Param("teamId")   Long teamId,
            @Param("type")     String type,
            @Param("lineType") String lineType
    );
}
