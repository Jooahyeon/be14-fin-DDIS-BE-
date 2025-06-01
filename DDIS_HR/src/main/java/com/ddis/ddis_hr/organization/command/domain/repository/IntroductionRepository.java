package com.ddis.ddis_hr.organization.command.domain.repository;

import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.IntroductionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntroductionRepository extends JpaRepository<IntroductionEntity, Long> {
    // 부서 소개 조회 (introduction_type='부서' AND department_id=:departmentId)
    Optional<IntroductionEntity> findByIntroductionTypeAndDepartmentId(String introductionType, Long departmentId);

    // 팀 소개 조회 (introduction_type='팀' AND team_id=:teamId)
    Optional<IntroductionEntity> findByIntroductionTypeAndTeamId(String introductionType, Long teamId);

    // 부서 소개 삭제
    void deleteByIntroductionTypeAndDepartmentId(String introductionType, Long departmentId);

    // 팀 소개 삭제
    void deleteByIntroductionTypeAndTeamId(String introductionType, Long teamId);
}
