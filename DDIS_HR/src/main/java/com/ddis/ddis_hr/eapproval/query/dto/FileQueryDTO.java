package com.ddis.ddis_hr.eapproval.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class FileQueryDTO {
    private String key;
    private String name;            // 파일명
    private Long size;               // 파일용량
    private String type;            // 파일종류




}
