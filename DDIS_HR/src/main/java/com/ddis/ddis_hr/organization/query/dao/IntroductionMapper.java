package com.ddis.ddis_hr.organization.query.dao;

import com.ddis.ddis_hr.organization.query.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface IntroductionMapper {

    // 모든 부서 조회
    List<DepartmentIntroductionQueryDTO> selectAllDepartments();

    // 단일 부서 상세 조회
    DepartmentIntroductionQueryDTO selectDepartmentById(@Param("departmentId") Long departmentId);

    // 특정 부서의 팀 목록 조회
    List<TeamIntroductionQueryDTO> selectTeamsByDepartmentId(@Param("departmentId") long departmentId);

    // 단일 팀 상세 조회
    TeamIntroductionQueryDTO selectTeamById(@Param("teamId") Long teamId);

    // 부서 소개만 조회
    String selectDepartmentIntroduction(@Param("departmentId") long departmentId);

    // 팀 소개만 조회
    String selectTeamIntroduction(@Param("teamId") long teamId);

    // 특정 팀의 직무 목록 조회
    List<JobIntroductionQueryDTO> selectJobsByTeamId(@Param("teamId") long teamId);

    EmployeeQueryDTO selectEmployeeById(Long employeeId);

    List<PositionDTO> selectPositionsByJobId(Long jobId);

    List<RankDTO> selectRanksByJobId(Long jobId);
}
