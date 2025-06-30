package com.ddis.ddis_hr.eapproval.query.mapper;

import com.ddis.ddis_hr.eapproval.query.dto.FindDrafterQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FindDrafterMapper {
        FindDrafterQueryDTO findDrafterInfo(@Param("empId")Long empId);

}

