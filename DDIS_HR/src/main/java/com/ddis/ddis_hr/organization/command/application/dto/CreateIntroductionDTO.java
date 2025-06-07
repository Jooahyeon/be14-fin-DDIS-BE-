package com.ddis.ddis_hr.organization.command.application.dto;

import lombok.*;
import software.amazon.awssdk.annotations.NotNull;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateIntroductionDTO {

    /*
     * - 부서 소개면 deptId, 팀 소개면 teamId를 각각 PathVariable로 받고,
     *   본 DTO엔 IntroContext만 담도록 설계.
     */
    private String introductionContext;
}
