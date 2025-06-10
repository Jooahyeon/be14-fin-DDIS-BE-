package com.ddis.ddis_hr.employee.query.service;

import com.ddis.ddis_hr.employee.query.dto.MyContractDTO;
import com.ddis.ddis_hr.member.security.CustomUserDetails;

import java.util.List;

public interface ContractQueryService {
    /** 본인 계약서 목록 조회 */
    List<MyContractDTO> findByEmployeeId(Long employeeId);

    /** 인사팀 전체 계약서 목록 조회 */
    List<MyContractDTO> findAllContracts();

    /**
     * 단일 계약서 권한 조회
     *  - 일반 사원: 본인 소유만, 인사팀: 전체 가능
     */
    MyContractDTO findOne(Long contractId, CustomUserDetails user);

    /**
     * presigned URL 생성
     *  - 내부에서 findOne 으로 권한·소유권 검사 후 URL 발급
     */
    String generateDownloadUrl(Long contractId, CustomUserDetails user);
}
