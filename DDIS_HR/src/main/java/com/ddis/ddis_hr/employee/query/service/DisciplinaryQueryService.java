package com.ddis.ddis_hr.employee.query.service;

// import com.ddis.ddis_hr.employee.query.dto.DisciplinaryListDTO;
import com.ddis.ddis_hr.employee.query.dto.DisciplinaryListDTO;
import com.ddis.ddis_hr.employee.query.dto.MyDisciplinaryDTO;
import com.ddis.ddis_hr.member.security.CustomUserDetails;

import java.util.List;

public interface DisciplinaryQueryService {
    /** 본인 징계 목록 조회 */
    List<MyDisciplinaryDTO> findByEmployeeId(Long employeeId);

    /** 인사팀 전체 징계 목록 조회 */
     List<DisciplinaryListDTO> findAll();

    /**
     * 단일 징계 메타 조회
     *  - 일반 사원: 본인 소유만, 인사팀: 전체 가능
     */
    MyDisciplinaryDTO findOne(Integer disciplinaryId, CustomUserDetails user);

    /**
     * presigned URL 생성
     *  - 내부에서 findOne 으로 권한·소유권 검사 후 URL 발급
     */
    String generateDownloadUrl(Integer disciplinaryId, CustomUserDetails user);
}
