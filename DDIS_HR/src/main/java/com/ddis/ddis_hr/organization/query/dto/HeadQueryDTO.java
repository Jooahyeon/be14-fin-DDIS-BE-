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
public class HeadQueryDTO {
    private Long headId;
    private String headName;
    private String headCode;
    private List<DepartmentQueryDTO> departments;  // 이 본부 소속 부서들 목록
}
