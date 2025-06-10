package com.ddis.ddis_hr.menu.query.service;

import com.ddis.ddis_hr.menu.query.dto.MenuDTO;

import java.util.List;

public interface FavoriteMenuQueryService {
    List<MenuDTO> getFavoriteMenus(Long employeeId);
}
