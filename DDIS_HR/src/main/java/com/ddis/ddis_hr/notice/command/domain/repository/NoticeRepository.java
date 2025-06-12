package com.ddis.ddis_hr.notice.command.domain.repository;

import com.ddis.ddis_hr.notice.command.domain.aggregate.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    // 특정 사원의 모든 알림 조회
    Optional<NoticeEntity> findByNoticeIdAndEmployeeEmployeeId(Long noticeId, Long employeeId);
}
