package com.ddis.ddis_hr.employee.query.service;

import com.ddis.ddis_hr.employee.query.dao.ContractMapper;
import com.ddis.ddis_hr.employee.query.dto.ContractListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContractQueryServiceImpl implements ContractQueryService {

    private final ContractMapper mapper;

    @Override
    public List<ContractListDTO> findAll() {
        return mapper.selectAll();
    }
}
