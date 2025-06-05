package com.ddis.ddis_hr.payroll.query.dto;

import java.time.LocalDate;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RetirementSummaryDTO {
    private Long employeeId;
    private String employeeName;
    private LocalDate retirementDate;
    private LocalDate provisionDate;
    private String provisionSituation;
    private String remark;
    private int retireTotal;
    private int retireIncomeTax;
    private int provisionActual;
}
