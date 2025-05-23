package com.ddis.ddis_hr.member.command.domain.aggregate.vo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestRegistUserVO {
    private String userName;
    private String userId;
    private String userPwd;
    private String userNickname;
    private String userEmail;
    private String userTel;
    private String userGender;
    private String userBirthday;
    private String inviteCode;
}
