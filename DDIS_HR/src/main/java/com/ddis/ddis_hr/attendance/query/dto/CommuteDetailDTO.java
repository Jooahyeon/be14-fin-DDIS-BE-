package com.ddis.ddis_hr.attendance.query.dto;

import lombok.Data;
import java.util.List;

@Data
public class CommuteDetailDTO {

    // 사원 정보
    private CommuteEmployeeInfoDTO employeeInfo;

    // 출퇴근 리스트
    private List<MyCommuteQueryDTO> commuteList;

}
