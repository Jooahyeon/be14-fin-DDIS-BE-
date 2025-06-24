package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.ApproverInfoQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.FindDrafterQueryDTO;

public class ApproverFactory {

    public static ApproverInfoQueryDTO fromDrafter(FindDrafterQueryDTO drafter,
                                                   int step,
                                                   String type,
                                                   String typeLabel) {
        ApproverInfoQueryDTO dto = new ApproverInfoQueryDTO();
        dto.setStep(step);
        dto.setEmployeeId(drafter.getEmpId());
        dto.setEmployeeName(drafter.getName());
        dto.setTeamName(drafter.getTeamName());
        dto.setDepartmentName(drafter.getDepartmentName());
        dto.setRankId(drafter.getRankId());
        dto.setRankName(drafter.getRankName());
        dto.setRankOrder(drafter.getRankOrder());
        dto.setType(type);
        dto.setTypeLabel(typeLabel);
        dto.setLineType("ACTUAL");
        return dto;
    }
}