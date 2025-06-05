package com.ddis.ddis_hr.organization.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamRequestDTO {

    private String teamName;
    private String departmentName;
}
