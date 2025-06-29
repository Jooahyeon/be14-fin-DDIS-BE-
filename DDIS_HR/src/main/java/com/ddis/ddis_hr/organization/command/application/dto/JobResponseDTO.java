package com.ddis.ddis_hr.organization.command.application.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResponseDTO {
    private Long id;
    private String jobName;
    private String jobCode;
    private List<String> jobRole;
    private List<String> jobNeed;
    private List<String> jobNecessary;
    private List<String> jobPreference;
    private Long teamId;
}
