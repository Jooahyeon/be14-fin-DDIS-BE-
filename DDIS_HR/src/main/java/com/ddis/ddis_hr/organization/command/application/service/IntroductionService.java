package com.ddis.ddis_hr.organization.command.application.service;

import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.IntroductionEntity;

public interface IntroductionService {

    // ===== 부서 소개 CRUD =====
    IntroductionEntity createDepartmentIntroduction(Long departmentId, String introductionContext);
    IntroductionEntity updateDepartmentIntroduction(Long departmentId, String introductionContext);
    void deleteDepartmentIntroduction(Long departmentId);

    // ===== 팀 소개 CRUD =====
    IntroductionEntity createTeamIntroduction(Long teamId, String introductionContext);
    IntroductionEntity updateTeamIntroduction(Long teamId, String introductionContext);
    void deleteTeamIntroduction(Long teamId);
}