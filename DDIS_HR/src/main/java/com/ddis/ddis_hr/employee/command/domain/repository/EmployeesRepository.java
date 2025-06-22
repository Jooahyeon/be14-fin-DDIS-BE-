package com.ddis.ddis_hr.employee.command.domain.repository;


import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;

@Repository
public interface EmployeesRepository extends JpaRepository<Employee, Long> {


    @Query(value = "SELECT MAX(e.employee_id) " +
            "FROM employee e " +
            "WHERE e.employee_id LIKE CONCAT(:prefix, '%')",
            nativeQuery = true)
    Long findMaxIdByPrefixForUpdate(@Param("prefix") String prefix);
}
