package com.ddis.ddis_hr.employee.query.service;

import com.ddis.ddis_hr.employee.query.dao.DisciplinaryMapper;
import com.ddis.ddis_hr.employee.query.dto.DisciplinaryListDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DisciplinaryQueryServiceImpl implements DisciplinaryQueryService {

    private final DisciplinaryMapper disciplinaryMapper;

    public DisciplinaryQueryServiceImpl(DisciplinaryMapper disciplinaryMapper) {
        this.disciplinaryMapper = disciplinaryMapper;
    }

    @Override
    public List<DisciplinaryListDTO> findAll() {
        return disciplinaryMapper.selectAll();
    }

    @Override
    public List<DisciplinaryListDTO> findByEmployeeId(Long employeeId) {
        return disciplinaryMapper.selectByEmployeeId(employeeId);
    }
}
