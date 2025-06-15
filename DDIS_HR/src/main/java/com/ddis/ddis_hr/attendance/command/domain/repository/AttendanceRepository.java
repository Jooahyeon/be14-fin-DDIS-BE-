package com.ddis.ddis_hr.attendance.command.domain.repository;

import com.ddis.ddis_hr.attendance.command.domain.aggregate.Attendance;
import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByEmployeeAndWorkDate(Employee employee, LocalDate workDate);

    boolean existsByEmployeeAndWorkDate(Employee employee, LocalDate workDate);

    Optional<Attendance> findByEmployee_EmployeeIdAndWorkDate(Long employeeId, LocalDate workDate);

    List<Attendance> findByEmployee_EmployeeIdAndWorkDateBetween(Long employeeId, LocalDate start, LocalDate end);

}