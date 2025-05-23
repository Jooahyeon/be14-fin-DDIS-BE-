package com.ddis.ddis_hr.member.command.application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    private String userName;
    private String userId;
    private String userPwd;
    private String userNickname;
    private String userEmail;
    private String userTel;
    private String userGender;
    private String userBirthday;

    private java.util.Date userEnrollDt;
    private String role;

}
