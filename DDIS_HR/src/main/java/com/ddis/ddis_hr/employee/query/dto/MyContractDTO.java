package com.ddis.ddis_hr.employee.query.dto;

import lombok.*;

import java.util.Date;

@Data
public class MyContractDTO {
    private Long contractId;
    private String contractDescription;
    private Date contractReqDate;
    private Date contractDate;
    private Date contractEndDate;

    private String contractFileName;
    private String contractFileURL;     // 객체 키
    private Long contractFileSize;

    private Long employeeId;

}
