package com.ddis.ddis_hr.employee.command.application.service;

import com.ddis.ddis_hr.employee.command.application.dto.ContractEnrollDTO;

public interface ContractService {
    Long createContract(ContractEnrollDTO dto);
    void deleteContract(Long contractId);
}
