package com.ddis.ddis_hr.employee.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "disciplinary_file")
@Getter @Setter @NoArgsConstructor
public class DisciplinaryFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long disciplinaryFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplinary_id", nullable = false)
    private Disciplinary disciplinary;

    private String fileName;
    private String fileUrl;
    private Long fileSize;

    @Column(updatable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();
}

