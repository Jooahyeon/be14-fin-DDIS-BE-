package com.ddis.ddis_hr.eapproval.command.domain.repository;

import com.ddis.ddis_hr.eapproval.command.domain.entity.DocumentBox;
import com.ddis.ddis_hr.eapproval.command.domain.entity.DocumentBoxId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentBoxRepository extends JpaRepository<DocumentBox, DocumentBoxId> {
}
