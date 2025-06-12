package com.ddis.ddis_hr.eapproval.command.domain.repository;

import com.ddis.ddis_hr.eapproval.command.domain.entity.Draft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Draft 엔티티 CRUD 처리용 리포지토리
 */
@Repository
public interface DraftRepository extends JpaRepository <Draft, Long> {
    // 기본 CRUD 메서드(findById, save, delete 등)는 JpaRepository가 제공

}
