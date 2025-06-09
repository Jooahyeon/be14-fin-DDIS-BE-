package com.ddis.ddis_hr.organization.query.service;

import com.ddis.ddis_hr.organization.query.dao.IntroductionMapper;
//import com.ddis.ddis_hr.organization.query.dao.JobQueryMapper;
import com.ddis.ddis_hr.organization.query.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IntroductionQueryService {

    private final IntroductionMapper introductionMapper;
//    private final JobQueryMapper jobQueryMapper;

    @Autowired
    public IntroductionQueryService(IntroductionMapper introductionMapper) {
        this.introductionMapper = introductionMapper;
//        this.jobQueryMapper = jobQueryMapper;
    }

    // 모든 부서 조회
    public List<DepartmentIntroductionQueryDTO> getAllDepartments() {
        return introductionMapper.selectAllDepartments();
    }

    // 특정 부서 조회
    public DepartmentIntroductionQueryDTO getDepartmentById(Long departmentId) {
        return introductionMapper.selectDepartmentById(departmentId);
    }

    @Transactional(readOnly = true)
    public List<TeamIntroductionQueryDTO> getTeamsByDepartment(long departmentId) {
        return introductionMapper.selectTeamsByDepartmentId(departmentId);
    }

    // 특정 팀 조회
    public TeamIntroductionQueryDTO getTeamById(Long teamId) {
        return introductionMapper.selectTeamById(teamId);
    }

    @Transactional(readOnly = true)
    public List<JobIntroductionQueryDTO> getJobsByTeam(long teamId) {
        return introductionMapper.selectJobsByTeamId(teamId);
    }

    public EmployeeQueryDTO getEmployeeById(Long employeeId) {
        return introductionMapper.selectEmployeeById(employeeId);
    }

    public List<PositionDTO> getPositionsByJob(Long jobId) {
        return introductionMapper.selectPositionsByJobId(jobId);
    }

    public List<RankDTO> getRanksByJob(Long jobId) {
        return introductionMapper.selectRanksByJobId(jobId);
    }
}
