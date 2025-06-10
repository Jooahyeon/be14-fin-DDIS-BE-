package com.ddis.ddis_hr.eapproval.query.mapper;

import com.ddis.ddis_hr.eapproval.query.dto.ApprovalStepQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApprovalStepMapper {
    List<ApprovalStepQueryDTO> findApprovalStepsByBasePositionId(Long positionId);

}
