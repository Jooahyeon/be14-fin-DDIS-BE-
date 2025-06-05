package com.ddis.ddis_hr.organization.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DepartmentDTO {
    private Long departmentId;
    private String departmentName;
    private String departmentCode;

    private Long headId;

    private String headName; // 조인 시 활용
}
