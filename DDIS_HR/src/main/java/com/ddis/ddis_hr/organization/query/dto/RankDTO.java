package com.ddis.ddis_hr.organization.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RankDTO {
    private Long rankId;
    private String rankName;
    private Integer rankOrder;
    private String rankCode;
}
