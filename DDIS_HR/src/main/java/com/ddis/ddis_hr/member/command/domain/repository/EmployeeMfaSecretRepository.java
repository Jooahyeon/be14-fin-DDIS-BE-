package com.ddis.ddis_hr.member.command.domain.repository;

import com.ddis.ddis_hr.member.command.domain.aggregate.entity.EmployeeMfaSecret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeMfaSecretRepository extends JpaRepository<EmployeeMfaSecret, Long> {
}
