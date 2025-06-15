package com.ddis.ddis_hr.eapproval.command.domain.repository;

import com.ddis.ddis_hr.eapproval.command.domain.entity.DocumentAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentAttachmentRepository extends JpaRepository<DocumentAttachment, Long> {
    List<DocumentAttachment> findByDraftDocId(Long draftId);
}
