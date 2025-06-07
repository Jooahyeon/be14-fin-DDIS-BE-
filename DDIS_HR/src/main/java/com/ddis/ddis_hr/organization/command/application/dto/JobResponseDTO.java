package com.ddis.ddis_hr.organization.command.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResponseDTO {
    private Long id;
    private String jobName;
    private String jobCode;
    private String jobRole;
    private String jobNeed;
    private String jobNecessary;
    private String jobPreference;
    private Long teamId;
}
