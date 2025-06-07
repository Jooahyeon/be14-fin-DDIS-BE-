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
public class TeamQueryDTO {
    private Long teamId;
    private String teamName;
    private String teamCode;
    private List<EmployeeQueryDTO> members;
}
