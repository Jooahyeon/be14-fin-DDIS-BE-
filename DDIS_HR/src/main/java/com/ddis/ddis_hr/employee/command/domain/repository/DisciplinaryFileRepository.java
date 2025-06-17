package com.ddis.ddis_hr.employee.command.domain.repository;

import com.ddis.ddis_hr.employee.command.domain.aggregate.DisciplinaryFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaryFileRepository extends JpaRepository<DisciplinaryFile, Long> { }
