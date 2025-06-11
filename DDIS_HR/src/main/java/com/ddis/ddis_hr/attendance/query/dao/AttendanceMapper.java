package com.ddis.ddis_hr.attendance.query.dao;

import com.ddis.ddis_hr.attendance.query.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface AttendanceMapper {

    EmployeeInfoQueryDTO findById(Long employeeId);

    List<PersonalScheduleQueryDTO> findPersonalSchedules(Long employeeId);

    List<MeetingQueryDTO> findMeetings(Long teamId);

    List<AttendanceQueryDTO> findWorkStatuses(@Param("teamId") Long teamId,
                                              @Param("employeeId") Long employeeId,
                                              @Param("statuses") List<String> statuses);

    List<MeetingQueryDTO> findMeetingsToday(@Param("teamId") Long teamId,
                                            @Param("today") LocalDate today);

    List<PersonalScheduleQueryDTO> findSchedulesToday(@Param("employeeId") Long employeeId,
                                                      @Param("today") LocalDate today);

    List<TeamWorkStatusQueryDTO> findTodayTeamStatuses(@Param("teamId") Long teamId,
                                                       @Param("today") String today);

    String findTeamNameById(Long teamId);

    MyWorkStatusQueryDTO findMyWorkStatus(@Param("employeeId") Long employeeId,
                                          @Param("today") String today);

}
