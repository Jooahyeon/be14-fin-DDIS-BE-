package com.ddis.ddis_hr.organization.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PositionDTO {
    private Long positionId;
    private String positionName;
    private String positionCode;
}
