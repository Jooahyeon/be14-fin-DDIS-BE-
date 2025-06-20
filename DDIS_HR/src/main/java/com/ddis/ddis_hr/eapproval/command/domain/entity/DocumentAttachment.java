package com.ddis.ddis_hr.eapproval.command.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "document_attachment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long id;

    // 기안 테이블과 N:1 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id", nullable = false)
    private DraftDocument draftDocument;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    // S3 키 또는 URL
    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    private Long draftDocId; // ← 이 필드명이 없으면 findByDraftDocId 사용 불가

}
