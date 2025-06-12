package com.ddis.ddis_hr.notice.query.dao;

import com.ddis.ddis_hr.notice.query.dto.NoticeQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {

    // 사원ID로 알림 목록 조회
    List<NoticeQueryDTO> selectByEmployeeId(@Param("employeeId") Long employeeId);
}
