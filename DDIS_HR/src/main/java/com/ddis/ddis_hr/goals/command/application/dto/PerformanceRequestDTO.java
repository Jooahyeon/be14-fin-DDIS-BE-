package com.ddis.ddis_hr.goals.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceRequestDTO {

    private Double performanceValue;
    private String selfreviewContent;
    private Long selfCreatedAt;
    private Long employeeIdSelfreviewer;
    private Double fileSize;
    private String attachmentKey;
    private String originalFileName;
    private String fileType;

}
