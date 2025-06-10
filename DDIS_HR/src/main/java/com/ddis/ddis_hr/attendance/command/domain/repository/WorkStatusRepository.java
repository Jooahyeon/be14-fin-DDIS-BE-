package com.ddis.ddis_hr.attendance.command.domain.repository;

import com.ddis.ddis_hr.attendance.command.domain.aggregate.WorkStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkStatusRepository extends JpaRepository<WorkStatus, String> {
}
