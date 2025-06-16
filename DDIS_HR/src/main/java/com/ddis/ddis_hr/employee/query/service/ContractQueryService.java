package com.ddis.ddis_hr.employee.query.service;

import com.ddis.ddis_hr.employee.query.dto.ContractListDTO;
import java.util.List;

public interface ContractQueryService {

    /** 전체 계약서 목록 */
    List<ContractListDTO> findAll();

    /** 사원별 계약서 목록 */
    List<ContractListDTO> findByEmployeeId(Long employeeId);
}
