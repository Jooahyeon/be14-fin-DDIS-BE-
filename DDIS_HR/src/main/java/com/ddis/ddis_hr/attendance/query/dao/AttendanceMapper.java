package com.ddis.ddis_hr.attendance.query.dao;

import com.ddis.ddis_hr.attendance.query.dto.*;
import org.apache.ibatis.annotations.Mapper;
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

    WeeklyOvertimeSummaryQueryDTO findWeeklyOvertimeSummary(@Param("employeeId") Long employeeId,
                                                       @Param("startDate") LocalDate startDate,
                                                       @Param("endDate") LocalDate endDate);

    WeeklyWorkDurationQueryDTO findWeeklyWorkDuration(@Param("employeeId") Long employeeId,
                                                 @Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate);

    LeaveStatusQueryDTO getLeaveStatus(@Param("employeeId") Long employeeId);

    List<LeaveHistoryQueryDTO> getLeaveHistoryByEmployeeId(@Param("employeeId") Long employeeId);

    List<LeaveHistoryQueryDTO> getPendingLeaveRequests(@Param("employeeId") Long employeeId);

    List<AllLeaveHistoryQueryDTO> getAllLeaveUsedList();

    List<AllLeaveHistoryQueryDTO> getAllLeavePendingList();

    List<MyCommuteQueryDTO> getMyCommuteList(@Param("employeeId") Long employeeId,
                                             @Param("startDate") String startDate,
                                             @Param("endDate") String endDate);

    List<AllCommuteSummaryDTO> getAllCommuteSummaryList(@Param("startDate") String startDate,
                                                        @Param("endDate") String endDate);

    CommuteEmployeeInfoDTO getEmployeeInfoById(@Param("employeeId") Long employeeId);

    List<MyCommuteQueryDTO> getCommuteDetailByIdAndDate(@Param("employeeId") Long employeeId,
                                                        @Param("startDate") String startDate,
                                                        @Param("endDate") String endDate);

    List<MyCommuteCorrectionQueryDTO> findCommuteCorrectionsByEmployeeId(@Param("employeeId") Long employeeId);

    List<MyCommuteCorrectionQueryDTO> findCommuteCorrectionsRequestByEmployeeId(@Param("employeeId") Long employeeId);

    List<AllCommuteCorrectionQueryDTO> findAllCommuteCorrections();

    List<AllCommuteCorrectionQueryDTO> findAllCommuteCorrectionsRequest();

}
