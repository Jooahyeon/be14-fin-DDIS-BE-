package com.ddis.ddis_hr.member.command.domain.aggregate.vo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseFindUserVO {
    private String employeeId;
    private String employeeName;
}
