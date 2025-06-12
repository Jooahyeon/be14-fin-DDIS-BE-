package com.ddis.ddis_hr.organization.command.domain.repository;

import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.AppointmentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentHistoryRepository extends JpaRepository<AppointmentHistoryEntity, Long> {

}