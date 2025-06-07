package com.ddis.ddis_hr.organization.query.dao;

import com.ddis.ddis_hr.organization.query.dto.AppointmentQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface AppointmentMapper {
    /**
     * 모든 appointment 를 조회
     */
    List<AppointmentQueryDTO> selectAll();

    /**
     * appointment_id 로 단건 조회
     */
    AppointmentQueryDTO selectByAppointmentId(@Param("appointmentId") Long appointmentId);

    /**
     * employee_id 로 해당 사원(직원)의 모든 appointment 조회
     */
    List<AppointmentQueryDTO> selectByEmployeeId(@Param("employeeId") Long employeeId);

    /**
     * status 로 appointment 조회 (예: "승인", "대기" 등)
     */
    List<AppointmentQueryDTO> selectByStatus(@Param("status") String status);
}
