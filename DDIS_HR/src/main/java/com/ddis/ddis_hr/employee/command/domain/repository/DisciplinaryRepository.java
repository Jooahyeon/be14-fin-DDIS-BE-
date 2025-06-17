package com.ddis.ddis_hr.employee.command.domain.repository;

import com.ddis.ddis_hr.employee.command.domain.aggregate.Disciplinary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaryRepository extends JpaRepository<Disciplinary, Long> { }
