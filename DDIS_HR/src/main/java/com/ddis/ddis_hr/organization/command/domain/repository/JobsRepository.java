package com.ddis.ddis_hr.organization.command.domain.repository;

import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobsRepository extends JpaRepository<JobEntity, Long> {
//    Optional<JobEntity> findByJobName(String jobName);
}
