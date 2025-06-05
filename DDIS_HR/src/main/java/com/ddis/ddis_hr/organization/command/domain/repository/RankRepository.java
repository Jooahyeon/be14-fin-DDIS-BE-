package com.ddis.ddis_hr.organization.command.domain.repository;

import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.RankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends JpaRepository<RankEntity, Long> {
}
