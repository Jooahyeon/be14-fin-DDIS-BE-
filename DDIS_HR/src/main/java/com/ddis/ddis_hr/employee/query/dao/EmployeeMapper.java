package com.ddis.ddis_hr.employee.query.dao;
import com.ddis.ddis_hr.payroll.query.dto.EmployeeSearchCondition;
import com.ddis.ddis_hr.payroll.query.dto.EmployeeSummaryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    List<EmployeeSummaryDTO> searchEmployees(EmployeeSearchCondition condition);

    EmployeeSummaryDTO findById(Long employeeId);
}
