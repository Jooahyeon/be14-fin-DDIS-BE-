package com.ddis.ddis_hr.employee.query.service;

import com.ddis.ddis_hr.employee.query.dao.EmployeeMapper;
import com.ddis.ddis_hr.employee.query.dto.EmployeeDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeQueryServiceImpl implements EmployeeQueryService {

    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeQueryServiceImpl(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeDTO findByMyId(Long employeeId) {
            return Optional.ofNullable(employeeMapper.findMyInfo(employeeId))
                    .orElseThrow(() ->
                            new EntityNotFoundException("해당 사번의 사원 정보를 찾을 수 없습니다. id=" + employeeId));
    }
}
