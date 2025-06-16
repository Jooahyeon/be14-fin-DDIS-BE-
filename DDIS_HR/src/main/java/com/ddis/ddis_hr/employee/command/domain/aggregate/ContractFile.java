package com.ddis.ddis_hr.employee.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "contract_file")
@Getter @Setter @NoArgsConstructor
public class ContractFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    private String fileName;
    private String fileUrl;   // S3 key
    private Long fileSize;

    @Column(updatable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();
}
