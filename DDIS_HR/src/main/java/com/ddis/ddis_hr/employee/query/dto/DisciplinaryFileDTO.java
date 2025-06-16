package com.ddis.ddis_hr.employee.query.dto;

import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DisciplinaryFileDTO {
    private Long             disciplinaryFileId;   // PK
    private Long             disciplinaryId;       // FK to disciplinary
    private String           fileName;             // file_name
    private String           fileUrl;              // file_url
    private Long             fileSize;             // file_size
    private LocalDateTime    uploadedAt;           // uploaded_at
}