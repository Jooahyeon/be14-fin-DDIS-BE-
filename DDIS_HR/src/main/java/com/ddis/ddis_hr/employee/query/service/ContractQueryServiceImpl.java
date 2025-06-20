package com.ddis.ddis_hr.employee.query.service;

import com.ddis.ddis_hr.employee.query.dao.ContractMapper;
import com.ddis.ddis_hr.employee.query.dto.ContractListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ContractQueryServiceImpl implements ContractQueryService {

    private final ContractMapper contractMapper;

    @Autowired
    public ContractQueryServiceImpl(ContractMapper contractMapper) {
        this.contractMapper = contractMapper;
    }

    @Override
    public List<ContractListDTO> findAll() {
        return contractMapper.selectAll();
    }

    @Override
    public List<ContractListDTO> findByEmployeeId(Long employeeId) {
        return contractMapper.selectByEmployeeId(employeeId);
    }
}
