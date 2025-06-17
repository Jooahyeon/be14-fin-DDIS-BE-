package com.ddis.ddis_hr.attendance.command.domain.repository;

import com.ddis.ddis_hr.attendance.command.domain.aggregate.LeaveAlertLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveAlertLogRepository extends JpaRepository<LeaveAlertLog, Long> {
}
