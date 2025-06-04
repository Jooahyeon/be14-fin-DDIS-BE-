package com.ddis.ddis_hr.member.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInfoDTO {
    private int userCode;
    private int userTotalPt;
    private int userLevel;
    private String userNickName;
    private Date userSuspEndDt;
    private int userRptCnt;
}
