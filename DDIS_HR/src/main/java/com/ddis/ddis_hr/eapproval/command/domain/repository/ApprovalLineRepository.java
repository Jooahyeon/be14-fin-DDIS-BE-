package com.ddis.ddis_hr.eapproval.command.domain.repository;

import com.ddis.ddis_hr.eapproval.command.domain.entity.ApprovalLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ApprovalLineRepository extends JpaRepository<ApprovalLine, Long> {
    // 문서 ID로 모든 결재라인 조회
//    List<ApprovalLine> findByDocId(Long docId);

    List<ApprovalLine> findByDocIdOrderByStep(Long docId);

    Optional<ApprovalLine> findByDocIdAndStep(Long docId, Integer step);


    long countByDocIdAndStepAndStatus(Long docId, Integer step, String status);


}
