package com.ddis.ddis_hr.attendance.command.domain.repository;

import com.ddis.ddis_hr.attendance.command.domain.aggregate.PersonalSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalScheduleRepository extends JpaRepository<PersonalSchedule, Long> {
}
