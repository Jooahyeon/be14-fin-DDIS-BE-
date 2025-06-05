package com.ddis.ddis_hr.organization.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentQueryDTO {
    private Long departmentId;
    private String departmentName;
    private String departmentCode;
    private List<TeamQueryDTO> teams;  // 이 부서 소속 팀들 목록
}
