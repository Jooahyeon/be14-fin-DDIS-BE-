package com.ddis.ddis_hr.organization.command.application.service;

import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.IntroductionEntity;
import com.ddis.ddis_hr.organization.command.domain.repository.IntroductionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IntroductionServiceImpl implements IntroductionService {
    private final IntroductionRepository introductionRepository;

    // ===== 부서 소개 =====

    @Override
    @Transactional
    public IntroductionEntity createDepartmentIntroduction(Long departmentId, String introductionContext) {
        // 이미 존재하는 부서 소개가 있는지 확인
        introductionRepository.findByIntroductionTypeAndDepartmentId("부서", departmentId)
                .ifPresent(existing -> {
                    throw new IllegalStateException("이미 존재하는 부서 소개입니다. departmentId=" + departmentId);
                });

        IntroductionEntity intro = new IntroductionEntity(introductionContext, "부서", departmentId, null);
        return introductionRepository.save(intro);
    }

    @Override
    @Transactional
    public IntroductionEntity updateDepartmentIntroduction(Long departmentId, String introductionContext) {
        IntroductionEntity intro = introductionRepository.findByIntroductionTypeAndDepartmentId("부서", departmentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 부서 소개가 존재하지 않습니다. departmentId=" + departmentId));

        intro.setIntroductionContext(introductionContext);
        return introductionRepository.save(intro);
    }

    @Override
    @Transactional
    public void deleteDepartmentIntroduction(Long departmentId) {
        if (!introductionRepository.findByIntroductionTypeAndDepartmentId("부서", departmentId).isPresent()) {
            throw new IllegalArgumentException("삭제할 부서 소개가 존재하지 않습니다. departmentId=" + departmentId);
        }
        introductionRepository.deleteByIntroductionTypeAndDepartmentId("부서", departmentId);
    }

    // ===== 팀 소개 =====

    @Override
    @Transactional
    public IntroductionEntity createTeamIntroduction(Long teamId, String introductionContext) {
        // 이미 존재하는 팀 소개가 있는지 확인
        introductionRepository.findByIntroductionTypeAndTeamId("팀", teamId)
                .ifPresent(existing -> {
                    throw new IllegalStateException("이미 존재하는 팀 소개입니다. teamId=" + teamId);
                });

        IntroductionEntity intro = new IntroductionEntity(introductionContext, "팀", null, teamId);
        return introductionRepository.save(intro);
    }

    @Override
    @Transactional
    public IntroductionEntity updateTeamIntroduction(Long teamId, String introductionContext) {
        IntroductionEntity intro = introductionRepository.findByIntroductionTypeAndTeamId("팀", teamId)
                .orElseThrow(() -> new IllegalArgumentException("해당 팀 소개가 존재하지 않습니다. teamId=" + teamId));

        intro.setIntroductionContext(introductionContext);
        return introductionRepository.save(intro);
    }

    @Override
    @Transactional
    public void deleteTeamIntroduction(Long teamId) {
        if (!introductionRepository.findByIntroductionTypeAndTeamId("팀", teamId).isPresent()) {
            throw new IllegalArgumentException("삭제할 팀 소개가 존재하지 않습니다. teamId=" + teamId);
        }
        introductionRepository.deleteByIntroductionTypeAndTeamId("팀", teamId);
    }
}
