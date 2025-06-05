package com.ddis.ddis_hr.organization.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JobDTO {
    private Long jobId;
    private String jobName;
    private String jobCode;
    private String jobRole;
    private String jobNeed;
    private String jobNecessary;
    private String jobPreference;

    private Long teamId;

    private String teamName; // 조인 시 활용
}
