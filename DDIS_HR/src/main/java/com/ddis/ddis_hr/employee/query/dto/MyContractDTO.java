package com.ddis.ddis_hr.employee.query.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyContractDTO {
    private Long contractId;
    private String contractDescription;
    private Date ContractReqDate;
    private Date ContractDate;
    private Date ContractEndDate;
    private String ContractFileName;
    private String ContractFileURL;
    private Long ContractFileSize;

    private Long employeeId;

}
