package com.ddis.ddis_hr.organization.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobIntroductionQueryDTO {
    private Long jobId;
    private String jobName;
    private String jobCode;
    private String jobRole;
    private String jobNeed;
    private String jobNecessary;
    private String jobPreference;
}
