package com.ddis.ddis_hr.employee.query.controller;

import com.ddis.ddis_hr.employee.query.dto.MyContractDTO;
import com.ddis.ddis_hr.employee.query.service.ContractQueryService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contracts")
public class ContractQueryController {

    private final ContractQueryService contractQueryService;

    @Autowired
    public ContractQueryController(ContractQueryService contractQueryService) {
        this.contractQueryService = contractQueryService;
    }

    /**
     * 1) 내 계약서 목록 조회
     *    - 호출 권한: 인증된 모든 사원 (ROLE_USER, ROLE_HR)
     *    - 동작: 사용자는 자신의 계약서 목록만 조회 가능
     *    - URL: GET /contracts/my
     */
    @GetMapping("/my")
    public ResponseEntity<List<MyContractDTO>> getMyContracts(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long empId = user.getEmployeeId();
        List<MyContractDTO> list = contractQueryService.findByEmployeeId(empId);
        return ResponseEntity.ok(list);
    }

    /**
     * 2) 인사팀 전체 계약서 목록 조회
     *    - 호출 권한: ROLE_HR
     *    - 동작: 인사팀은 전 사원의 계약서 목록을 조회 가능
     *    - URL: GET /contracts
     */
    @PreAuthorize("hasRole('HR')")
    @GetMapping
    public ResponseEntity<List<MyContractDTO>> getAllContracts() {
        List<MyContractDTO> list = contractQueryService.findAllContracts();
        return ResponseEntity.ok(list);
    }

    /**
     * 3) 계약서 다운로드 URL 발급
     *    - 호출 권한: ROLE_USER, ROLE_HR
     *    - 동작:
     *        - 일반 사원(ROLE_USER)은 자신의 contractId만 다운로드 가능
     *        - 인사팀(ROLE_HR)은 모든 사원의 contractId 다운로드 가능
     *    - URL: GET /contracts/{contractId}/download
     *    - 반환: { "url": "presigned-download-url" }
     */
    @PreAuthorize("hasAnyRole('USER','HR')")
    @GetMapping("/{contractId}/download")
    public ResponseEntity<Map<String, String>> downloadContract(
            @PathVariable Long contractId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        String presignedUrl = contractQueryService.generateDownloadUrl(contractId, user);
        return ResponseEntity.ok(Collections.singletonMap("url", presignedUrl));
    }
}
