package com.ddis.ddis_hr.attendance.command.domain.repository;

import com.ddis.ddis_hr.attendance.command.domain.aggregate.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingScheduleRepository extends JpaRepository<Meeting, Long> {
}

