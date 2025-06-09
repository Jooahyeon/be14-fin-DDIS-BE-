package com.ddis.ddis_hr.employee.query.service;

import com.ddis.ddis_hr.employee.query.dto.MyContractDTO;

import java.util.List;

public interface ContractQueryService {

    // 해당 사원 ID로 계약서 목록 조회
    List<MyContractDTO> findByEmployeeId(Long employeeId);
}
