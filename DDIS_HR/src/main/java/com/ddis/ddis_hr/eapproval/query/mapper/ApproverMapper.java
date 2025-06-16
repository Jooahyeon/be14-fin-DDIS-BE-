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
     * 직급 정렬값(rankOrder) + 조직 타입 + 조직 ID를 기준으로 결재자 1명을 조회한다.
     *
     * @param rankOrder 결재자 직급 정렬값
     * @param orgType 조직 구분 (team, department, head)
     * @param orgId 조직 ID
     */
    ApproverInfoQueryDTO findApproverByRankOrderAndOrg(
            @Param("rankOrder") int rankOrder,
            @Param("orgType")   String orgType,
            @Param("orgId")     Long orgId,
            @Param("step")      int step,        // 추가!
            @Param("type")      String type,     // "DRAFT" or "APPROVAL"
            @Param("lineType")  String lineType  // "AUTO" or "MANUAL"
    );
}
