package com.ddis.ddis_hr.payroll.query.service;
import com.ddis.ddis_hr.employee.query.dao.EmployeeMapper;
import com.ddis.ddis_hr.payroll.query.dto.EmployeeSearchCondition;
import com.ddis.ddis_hr.payroll.query.dto.EmployeeSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class EmployeeSearchService {

    private final EmployeeMapper employeeMapper;

    public List<EmployeeSummaryDTO> searchEmployees(EmployeeSearchCondition condition) {
        return employeeMapper.searchEmployees(condition);
    }

    public EmployeeSummaryDTO findById(Long employeeId) {
        EmployeeSummaryDTO result = employeeMapper.findById(employeeId);
//        if (result == null) {
//            throw new NoSuchElementException("사원 정보가 없습니다: " + employeeId);
//        }
        return result;
    }
}