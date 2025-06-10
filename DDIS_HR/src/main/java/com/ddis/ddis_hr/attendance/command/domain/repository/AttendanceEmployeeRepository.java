package com.ddis.ddis_hr.attendance.command.domain.repository;

import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceEmployeeRepository extends JpaRepository<Employee, Long> {
}