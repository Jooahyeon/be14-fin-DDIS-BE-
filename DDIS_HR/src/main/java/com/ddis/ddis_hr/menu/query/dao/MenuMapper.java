package com.ddis.ddis_hr.menu.query.dao;

import com.ddis.ddis_hr.menu.query.dto.MenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<MenuDTO> selectAllMenus();
}
