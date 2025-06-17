package com.ddis.ddis_hr.eapproval.query.mapper;

import com.ddis.ddis_hr.eapproval.command.domain.entity.DocumentBox;
import com.ddis.ddis_hr.eapproval.query.dto.ReferenceDocDTO;
import jakarta.transaction.Transactional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DocumentBoxMapper {
    List<ReferenceDocDTO> selectReferenceDocsByEmployeeId(@Param("employeeId") Long employeeId);

    void updateReadStatus(@Param("employeeId") Long employeeId,
                          @Param("docId") Long docId,
                          @Param("readAt") LocalDateTime readAt);

    List<ReferenceDocDTO> selectReceiverDocsByEmployeeId(Long employeeId);

    public interface DocumentBoxRepository extends JpaRepository<DocumentBox, Long> {
        @Modifying
        @Transactional
        @Query("DELETE FROM DocumentBox db WHERE db.docId = :docId AND db.employeeId <> :drafterId")
        void deleteAllExceptDrafter(@Param("docId") Long docId, @Param("drafterId") Long drafterId);
    }
}
