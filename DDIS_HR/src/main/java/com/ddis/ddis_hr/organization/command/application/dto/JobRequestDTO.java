package com.ddis.ddis_hr.organization.command.application.dto;

import lombok.*;
import software.amazon.awssdk.annotations.NotNull;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequestDTO {

    private String jobName;
    private String jobCode;

    // JSON 배열 문자열로 들어간다고 가정 (예: ["내용1","내용2",...])
    private List<String> jobRole;
    private List<String> jobNeed;
    private List<String> jobNecessary;
    private List<String> jobPreference;

    @NotNull
    private Long teamId;
}
