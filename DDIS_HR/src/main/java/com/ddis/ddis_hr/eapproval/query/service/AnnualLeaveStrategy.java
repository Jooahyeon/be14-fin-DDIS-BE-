package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.query.dto.ApproverInfoQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.FindDrafterQueryDTO;
import com.ddis.ddis_hr.eapproval.query.mapper.ApproverMapper;
import com.ddis.ddis_hr.eapproval.query.mapper.FindDrafterMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AnnualLeaveStrategy implements ApprovalLineStrategy {

    private static final Long HR_TEAM_ID = 6L; // 💡 실제 인사팀 ID로 교체 필요

    private final ApproverMapper approverMapper;
    private final FindDrafterMapper drafterMapper;

    // 생성자 주입 생략
    @Autowired // 명시적 생성자 주입
    public AnnualLeaveStrategy(ApproverMapper approverMapper, FindDrafterMapper drafterMapper) {
        this.approverMapper = approverMapper;
        this.drafterMapper = drafterMapper;
    }

    // 생성자 주입 생략
    @Override
    public List<ApproverInfoQueryDTO> createApprovalLine(Long drafterId) {

        log.info("▶▶ 연차 결재선 자동생성 시작: drafterId={}", drafterId);

        // ① 기안자 정보 조회
        FindDrafterQueryDTO drafter = drafterMapper.findDrafterInfo(drafterId);
        if (drafter == null) {
            log.warn("⚠ 기안자 정보를 찾을 수 없습니다. drafterId={}", drafterId);
            return new ArrayList<>();
        }

        List<ApproverInfoQueryDTO> result = new ArrayList<>();

        // ② 1단계: 기안자
        result.add(ApproverFactory.fromDrafter(drafter, 1, "기안", "기안"));

        // ③ 2단계: 팀 상급자 (같은 팀, 한 단계 위 직급)
        ApproverInfoQueryDTO teamSenior = approverMapper.findApproverByRankOrderAndOrg(
                drafter.getRankOrder() - 1,
                "team",
                drafter.getTeamId(),
                2,
                "결재",
                "ACTUAL"
        );
        if (teamSenior != null) result.add(teamSenior);

        // ④ 3단계: 인사팀 일반 직원
        ApproverInfoQueryDTO hrStaff = approverMapper.findLowestRankInTeam(
                HR_TEAM_ID, "결재", "ACTUAL"
        );
        if (hrStaff != null) {
            hrStaff.setStep(3);
            result.add(hrStaff);
        }

        // ⑤ 4단계: 인사팀 팀장
        ApproverInfoQueryDTO hrLead = approverMapper.findHighestRankInTeam(
                HR_TEAM_ID, "결재", "ACTUAL"
        );
        if (hrLead != null) {
            hrLead.setStep(4);
            result.add(hrLead);
        }

        log.info("✅ 연차 결재선 생성 완료. 총 {}단계", result.size());
        return result;
    }
}
