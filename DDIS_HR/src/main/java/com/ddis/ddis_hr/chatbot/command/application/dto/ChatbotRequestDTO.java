package com.ddis.ddis_hr.chatbot.command.application.dto;

import com.ddis.ddis_hr.attendance.command.application.dto.PersonalScheduleRequestDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ChatbotRequestDTO {

    Long employeeId;
    String employeeName;
    String departmentName;
    String teamName;
    Long positionId;
    String positionName;
    Long rankId;
    String rankName;
    String jobName;
    Date hireDate;  //입사일
    Integer remainingVacationDays;  //남은 휴가 일수
    Date meetingDate;   //회의날짜
    Integer vacationDays;
    Integer draftsCount;    //기안 문서
    Integer pendingCount;   //결재 대기 문서
    Integer rejectedCount;  //반려 문서
    List<TeamMemberDTO> teamMembers; //내가 속한 팀의 팀원
    List<PersonalScheduleRequestDTO> personalSchedules;
}
