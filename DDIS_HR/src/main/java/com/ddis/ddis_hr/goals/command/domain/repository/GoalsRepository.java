package com.ddis.ddis_hr.goals.command.domain.repository;

import com.ddis.ddis_hr.goals.command.domain.aggregate.Goals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalsRepository extends JpaRepository<Goals, Long> {
}
