package com.ddis.ddis_hr.employee.query.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ContractListDTO {
    private Integer contractId;
    private String  contractDescription;
    private Date contractDate;
    private Date requestDate;
    private Date endDate;

    private List<ContractFileDTO> fileList;
    private Long    employeeId;
    private String  employeeName;
}
