package com.ddis.ddis_hr.eapproval.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {

    private Long docId;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime submittedAt;
    private String status;

}
