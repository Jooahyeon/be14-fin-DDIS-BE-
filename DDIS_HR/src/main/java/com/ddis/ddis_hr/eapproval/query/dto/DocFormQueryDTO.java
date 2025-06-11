package com.ddis.ddis_hr.eapproval.query.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DocFormQueryDTO {
    private Long formId;
    private String formName;
    private String formContent;
    private Boolean isDeleted;
}