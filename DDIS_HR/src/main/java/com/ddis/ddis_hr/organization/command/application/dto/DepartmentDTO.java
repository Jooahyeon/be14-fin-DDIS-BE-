package com.ddis.ddis_hr.organization.command.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DepartmentDTO {
    private Long departmentId;
    private String departmentName;
    private String departmentCode;
    private List<TeamDTO> team;
}
