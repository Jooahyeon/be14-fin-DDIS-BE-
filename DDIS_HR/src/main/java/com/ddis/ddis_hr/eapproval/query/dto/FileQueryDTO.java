package com.ddis.ddis_hr.eapproval.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class FileQueryDTO {
    private String name;            // 파일명
    private int size;               // 파일용량
    private String type;            // 파일종류
}
