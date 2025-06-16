package com.ddis.ddis_hr.employee.query.service;

import com.ddis.ddis_hr.employee.query.dao.DisciplinaryMapper;
import com.ddis.ddis_hr.employee.query.dto.DisciplinaryListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DisciplinaryQueryServiceImpl implements DisciplinaryQueryService {

    private final DisciplinaryMapper mapper;

    @Override
    public List<DisciplinaryListDTO> findAll() {
        return mapper.selectAll();
    }
}
