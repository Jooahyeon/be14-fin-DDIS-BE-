package com.ddis.ddis_hr.organization.query.controller;

import com.ddis.ddis_hr.organization.query.dto.*;
import com.ddis.ddis_hr.organization.query.service.IntroductionQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/introduction")
public class IntroductionQueryController {

    private final IntroductionQueryService introductionQueryService;

    @Autowired
    public IntroductionQueryController(IntroductionQueryService introductionQueryService) {
        this.introductionQueryService = introductionQueryService;
    }

    /**
     * 전체 부서 리스트 조회
     * GET /api/org/departments
     */
    @GetMapping("/department")
    public ResponseEntity<List<DepartmentIntroductionQueryDTO>> getAllDepartments() {
        List<DepartmentIntroductionQueryDTO> departments = introductionQueryService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }


    /**
     * 단일 부서 상세 조회
     * GET /api/org/departments/{deptId}
     */
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<DepartmentIntroductionQueryDTO> getDepartmentById(
            @PathVariable long departmentId) {
        DepartmentIntroductionQueryDTO department =
                introductionQueryService.getDepartmentById(departmentId);
        if (department == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(department);
    }

    //특정 부서의 팀 목록 조회 (department_id → Team 목록)
    @GetMapping("/department/{departmentId}/team")
    public ResponseEntity<List<TeamIntroductionQueryDTO>> getTeamsByDepartment(
            @PathVariable long departmentId) {
        List<TeamIntroductionQueryDTO> teams =
                introductionQueryService.getTeamsByDepartment(departmentId);
        return ResponseEntity.ok(teams);
    }

    /**
     * 단일 팀 상세 조회
     * GET /api/org/teams/{teamId}
     */
    @GetMapping("/team/{teamId}")
    public TeamIntroductionQueryDTO getTeam(@PathVariable("teamId") Long teamId) {
        return introductionQueryService.getTeamById(teamId);
    }

    //  특정 팀의 직무 목록 조회 (team_id → Job 목록)
    @GetMapping("/team/{teamId}/job")
    public ResponseEntity<List<JobIntroductionQueryDTO>> getJobsByTeam(
            @PathVariable long teamId) {
        List<JobIntroductionQueryDTO> jobs = introductionQueryService.getJobsByTeam(teamId);
        return ResponseEntity.ok(jobs);
    }

    /** 특정 직무(jobId) 에 속한 직책 목록 조회 */
    @GetMapping("/job/{jobId}/positions")
    public ResponseEntity<List<PositionDTO>> getPositionsByJob(
            @PathVariable Long jobId) {
        List<PositionDTO> list = introductionQueryService.getPositionsByJob(jobId);
        return ResponseEntity.ok(list);
    }

    /** 특정 직무(jobId) 에 속한 직급 목록 조회 */
    @GetMapping("/job/{jobId}/ranks")
    public ResponseEntity<List<RankDTO>> getRanksByJob(
            @PathVariable Long jobId) {
        List<RankDTO> list = introductionQueryService.getRanksByJob(jobId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<EmployeeQueryDTO> getEmployee(
            @PathVariable Long employeeId) {
        EmployeeQueryDTO emp = introductionQueryService.getEmployeeById(employeeId);
        if (emp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(emp);
    }
}