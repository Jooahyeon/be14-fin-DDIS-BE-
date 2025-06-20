package com.ddis.ddis_hr.eapproval.command.domain.repository;

import com.ddis.ddis_hr.eapproval.command.domain.entity.DocumentBox;
import com.ddis.ddis_hr.eapproval.command.domain.entity.DocumentBoxId;
import jakarta.transaction.Transactional;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentBoxRepository extends JpaRepository<DocumentBox, Long> {
//    @Modifying
//    @Transactional
//    @Query("DELETE FROM ddisdb.document_box db WHERE db.doc_id = :docId AND db.employee_id <> :drafterId")
//    void deleteAllExceptDrafter(@Param("docId") Long docId, @Param("drafterId") Long drafterId);
}