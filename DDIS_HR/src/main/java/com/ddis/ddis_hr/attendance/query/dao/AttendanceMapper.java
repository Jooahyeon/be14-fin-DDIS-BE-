package com.ddis.ddis_hr.attendance.query.dao;

import com.ddis.ddis_hr.attendance.query.dto.AttendanceQueryDTO;
import com.ddis.ddis_hr.attendance.query.dto.EmployeeInfoQueryDTO;
import com.ddis.ddis_hr.attendance.query.dto.MeetingQueryDTO;
import com.ddis.ddis_hr.attendance.query.dto.PersonalScheduleQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface AttendanceMapper {

    EmployeeInfoQueryDTO findById(Long employeeId);

    List<PersonalScheduleQueryDTO> findPersonalSchedules(Long employeeId);

    List<MeetingQueryDTO> findMeetings(Long teamId);

    List<AttendanceQueryDTO> findWorkStatuses(@Param("teamId") Long teamId,
                                              @Param("employeeId") Long employeeId,
                                              @Param("statuses") List<String> statuses);
}
