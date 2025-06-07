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
public class TeamIntroductionQueryDTO {
    private Long teamId;                   // 팀 ID
    private String teamName;               // 팀명 (예: "PC개발팀")
    private String introductionContext;    // introduction 테이블의 팀 소개문
    private List<JobIntroductionQueryDTO> jobs;
}
