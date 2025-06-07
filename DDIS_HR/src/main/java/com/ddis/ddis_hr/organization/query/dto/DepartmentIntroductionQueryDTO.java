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
public class DepartmentIntroductionQueryDTO {
    private Long departmentId;
    private String departmentName;
    private String introductionContext;
    private List<TeamIntroductionQueryDTO> teams;

}