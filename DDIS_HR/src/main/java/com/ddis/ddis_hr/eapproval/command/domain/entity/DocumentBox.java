package com.ddis.ddis_hr.eapproval.command.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "document_box")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA용
@AllArgsConstructor                                // Builder, 직접 new 용
@Builder                                           // 빌더 지원
public class DocumentBox {

    @EmbeddedId
    private DocumentBoxId id;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;
}  // '기안자','결재자','협조자','수신자','참조자'

    // constructor, getters/setters



