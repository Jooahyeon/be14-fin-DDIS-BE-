package com.ddis.ddis_hr.organization.query.dao;

import com.ddis.ddis_hr.organization.query.dto.AppointmentHistoryQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface AppointmentHistoryMapper {

    /* 모든 appointment 를 조회 */
    List<AppointmentHistoryQueryDTO> selectAllHistory();

    /* appointment_id 로 단건 조회 */
    List<AppointmentHistoryQueryDTO> selectByAppointmentId(@Param("appointmentId") Long appointmentId);

    /* employee_id 로 해당 사원(직원)의 모든 appointment 조회 */
    List<AppointmentHistoryQueryDTO> selectByEmployeeId(@Param("employeeId") Long employeeId);

    // 발령 유형 별 조회
    List<AppointmentHistoryQueryDTO> selectByAppointmentType(@Param("appointmentType") String appointmentType);
}
