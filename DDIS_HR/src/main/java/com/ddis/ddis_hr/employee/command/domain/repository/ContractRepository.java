package com.ddis.ddis_hr.employee.command.domain.repository;


import com.ddis.ddis_hr.employee.command.domain.aggregate.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
