package com.ddis.ddis_hr.member.command.application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PersonalMemberDTO {
    private int userCode;
    private String userName;
    private String userId;
    private String userPassword;
    private String userPhone;
    private String userEmail;
    private String userGender;
    private String userBirthday;
    private java.util.Date userEnrollDatetime;

}
