package com.ddis.ddis_hr.employee.command.application.service;

import com.ddis.ddis_hr.employee.command.application.dto.DisciplinaryEnrollDTO;

public interface DisciplinaryService {
    /**
     * 징계 등록
     * @param dto 등록 정보 (사원ID, 설명, 일자, 업로드된 파일 메타)
     * @return 생성된 disciplinaryId
     */
    Long createDisciplinary(DisciplinaryEnrollDTO dto);

    /**
     * 징계 삭제
     * @param disciplinaryId 삭제할 징계 PK
     */
    void deleteDisciplinary(Long disciplinaryId);
}

