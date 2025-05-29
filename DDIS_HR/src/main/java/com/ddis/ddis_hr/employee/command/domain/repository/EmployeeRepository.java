package com.ddis.ddis_hr.employee.command.domain.repository;

import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
