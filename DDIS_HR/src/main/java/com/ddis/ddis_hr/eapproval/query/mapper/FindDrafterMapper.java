package com.ddis.ddis_hr.eapproval.query.mapper;

import com.ddis.ddis_hr.eapproval.query.dto.ApproverInfoQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.FindDrafterQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FindDrafterMapper {
        FindDrafterQueryDTO findDrafterInfo(@Param("empId")Long empId);

        ApproverInfoQueryDTO findApproverByTeamAndPosition(@Param("teamId") Long teamId,
                                                           @Param("positionName") String positionName);
        ApproverInfoQueryDTO findApproverByDepartmentAndPosition(@Param("departmentId") Long deptId,
                                                                 @Param("positionName") String positionName);
        ApproverInfoQueryDTO findApproverByHeadAndPosition(@Param("headId") Long headId,
                                                           @Param("positionName") String positionName);
}

