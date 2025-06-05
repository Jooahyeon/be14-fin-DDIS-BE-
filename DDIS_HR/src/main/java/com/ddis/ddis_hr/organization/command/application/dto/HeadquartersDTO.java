package com.ddis.ddis_hr.organization.command.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HeadquartersDTO {
    private Long headId;
    private String headName;
    private String headCode;
    private List<DepartmentDTO> department;
}
