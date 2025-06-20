package com.ddis.ddis_hr.employee.query.service;

import com.ddis.ddis_hr.employee.query.dto.ContractListDTO;
import java.util.List;

public interface ContractQueryService {

    List<ContractListDTO> findAll();

    List<ContractListDTO> findByEmployeeId(Long employeeId);
}
