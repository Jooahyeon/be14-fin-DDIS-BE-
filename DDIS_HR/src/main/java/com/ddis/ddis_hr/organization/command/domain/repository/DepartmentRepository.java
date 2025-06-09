package com.ddis.ddis_hr.organization.command.domain.repository;

import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.DepartmentEntity;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.HeadquartersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
    DepartmentEntity findTopByOrderByDepartmentCodeDesc();
    Optional<DepartmentEntity> findByDepartmentId(Long departmentId);
}
