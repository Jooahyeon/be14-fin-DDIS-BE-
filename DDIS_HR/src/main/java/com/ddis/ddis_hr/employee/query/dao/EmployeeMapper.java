package com.ddis.ddis_hr.employee.query.dao;
import com.ddis.ddis_hr.employee.query.dto.EmployeeDTO;
import com.ddis.ddis_hr.employee.query.dto.EmployeeHrDTO;
import com.ddis.ddis_hr.employee.query.dto.EmployeeListDTO;
import com.ddis.ddis_hr.employee.query.dto.EmployeePublicDTO;
import com.ddis.ddis_hr.payroll.query.dto.EmployeeSearchCondition;
import com.ddis.ddis_hr.payroll.query.dto.EmployeeSummaryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    List<EmployeeSummaryDTO> searchEmployees(EmployeeSearchCondition condition);

    EmployeeSummaryDTO findById(Long employeeId);

    EmployeeDTO findMyInfo(@Param("employeeId") Long employeeId);

    EmployeePublicDTO findPublicById(@Param("employeeId") Long employeeId);

    EmployeeHrDTO findHrById(@Param("employeeId") Long employeeId);

    List<EmployeeListDTO> findAll();
}
