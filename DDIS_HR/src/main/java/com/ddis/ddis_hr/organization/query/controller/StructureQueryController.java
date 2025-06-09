package com.ddis.ddis_hr.organization.query.controller;

import com.ddis.ddis_hr.organization.query.dto.DepartmentQueryDTO;
import com.ddis.ddis_hr.organization.query.dto.EmployeeQueryDTO;
import com.ddis.ddis_hr.organization.query.dto.HeadQueryDTO;
import com.ddis.ddis_hr.organization.query.dto.TeamQueryDTO;
import com.ddis.ddis_hr.organization.query.service.StructureQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/structure")
public class StructureQueryController {

    private final StructureQueryService structureQueryService;

    @Autowired
    public StructureQueryController(StructureQueryService structureQueryService) {
        this.structureQueryService = structureQueryService;
    }


    /** 특정 본부 계층(부서→팀→팀원)만 조회 */
    @GetMapping("/hierarchy")
    public List<HeadQueryDTO> getFullHierarchy() {
        return structureQueryService.getOrganizationHierarchy();
    }

     /** 특정 본부 계층(부서→팀→팀원)만 조회 */
    @GetMapping("/heads/{headId}")
    public HeadQueryDTO getHeadHierarchy(@PathVariable("headId") Long headId) {
        return structureQueryService.getHeadHierarchy(headId);
    }

    /** 특정 부서 계층(팀→팀원)만 조회 */
    @GetMapping("/departments/{deptId}")
    public DepartmentQueryDTO getDepartmentHierarchy(@PathVariable("deptId") Long deptId) {
        return structureQueryService.getDepartmentHierarchy(deptId);
    }

    /** 특정 팀 계층(팀원)만 조회 */
    @GetMapping("/teams/{teamId}")
    public TeamQueryDTO getTeamHierarchy(@PathVariable("teamId") Long teamId) {
        return structureQueryService.getTeamHierarchy(teamId);
    }

    /** 특정 팀의 팀원 목록만 조회 */
    @GetMapping("/teams/{teamId}/members")
    public List<EmployeeQueryDTO> getMembers(@PathVariable("teamId") Long teamId) {
        return structureQueryService.getMembersByTeam(teamId);
    }

    /** 사원 상세 조회 */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<EmployeeQueryDTO> getEmployeeDetail(@PathVariable Long employeeId) {
        EmployeeQueryDTO dto = structureQueryService.getEmployeeById(employeeId);
        if (dto == null) {
            // 존재하지 않는 사원 ID면 404 Not Found를 내려준다.
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    /** 모든 본부만 조회 */
    @GetMapping("/heads")
    public ResponseEntity<List<HeadQueryDTO>> getAllHeads() {
        List<HeadQueryDTO> heads = structureQueryService.getAllHeads();
        return ResponseEntity.ok(heads);
    }

    /** 특정 본부 이름 조회 */
    @GetMapping("/head/{headId}/name")
    public ResponseEntity<String> getHeadName(@PathVariable Long headId) {
        String headName = structureQueryService.getHeadNameById(headId);
        if (headName == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(headName);
    }

    /** 특정 본부 코드 조회 */
    @GetMapping("/head/{headId}/code")
    public ResponseEntity<String> getHeadCode(@PathVariable Long headId) {
        String headCode = structureQueryService.getHeadCodeById(headId);
        if (headCode == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(headCode);
    }

    /** 특정 본부의 부서 정보(+부장) 조회 */
    @GetMapping("/heads/{headId}/departments")
    public ResponseEntity<List<DepartmentQueryDTO>> getDepartmentsByHeadId(@PathVariable Long headId) {
        List<DepartmentQueryDTO> departments = structureQueryService.getDepartmentsByHeadId(headId);
        if (departments == null || departments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(departments);
    }

    /** 특정 부서의 부서장 조회*/
    @GetMapping("/departments/{deptId}/manager")
    public ResponseEntity<EmployeeQueryDTO> getDeptManager(@PathVariable("deptId") Long deptId) {
        EmployeeQueryDTO manager = structureQueryService.getDeptManagerByDepartmentId(deptId);
        if (manager == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(manager);
    }
}
