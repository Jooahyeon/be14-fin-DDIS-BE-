package com.ddis.ddis_hr.goals.command.domain.repository;

import com.ddis.ddis_hr.goals.command.domain.aggregate.SelfreviewFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfreviewFileRepository extends JpaRepository<SelfreviewFile, Long> {
}
