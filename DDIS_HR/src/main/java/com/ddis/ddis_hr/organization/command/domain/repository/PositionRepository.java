package com.ddis.ddis_hr.organization.command.domain.repository;

import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<PositionEntity, Long> {
}
