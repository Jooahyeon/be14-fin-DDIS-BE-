package com.ddis.ddis_hr.eapproval.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DraftRequestDTO {
    private String department;
    private String title;
    private String content;
    private int retentionPeriod;        // 보존 연한 (년 단위)
    private Long writerId;              // 기안자
    private List<Long> approvers;       // 결재자
    private List<Long> cooperators;     // 협조자
    private List<Long> receivers;       // 수신자
    private List<Long> ccs;             // 참조자

}
