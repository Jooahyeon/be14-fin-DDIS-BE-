package com.ddis.ddis_hr.employee.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ContractFileDTO {
    private Long             contractFileId;
    private Long             contractId;
    private String           fileName;
    private String           fileUrl;
    private Long             fileSize;
    private LocalDateTime uploadedAt;
}
