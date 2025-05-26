package com.ddis.ddis_hr.member.command.domain.repository;

import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;



public interface  UserRepository extends JpaRepository<Employee, Long> {
    Employee findByEmployeeId(Long employeeId);
}
