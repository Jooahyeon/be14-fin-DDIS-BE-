package com.ddis.ddis_hr.organization.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IntroductionResponseDTO {
    private Long introductionId;
    private Long departmentId;
    private Long teamId;
    private String introductionContext;
    private LocalDate createdAt;
}
