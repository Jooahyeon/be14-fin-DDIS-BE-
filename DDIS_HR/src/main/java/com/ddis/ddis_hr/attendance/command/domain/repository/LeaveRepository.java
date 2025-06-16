package com.ddis.ddis_hr.attendance.command.domain.repository;

import com.ddis.ddis_hr.attendance.command.domain.aggregate.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
}
