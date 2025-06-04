package com.ddis.ddis_hr.organization.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TeamDTO {
    private Long teamId;
    private String teamName;
    private String teamCode;

    private Long departmentId;

    private String departmentName; // 조인 시 활용
}
