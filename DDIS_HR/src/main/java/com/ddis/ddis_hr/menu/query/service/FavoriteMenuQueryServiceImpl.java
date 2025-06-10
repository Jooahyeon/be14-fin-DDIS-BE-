package com.ddis.ddis_hr.menu.query.service;

import com.ddis.ddis_hr.menu.query.dao.FavoriteMenuMapper;
import com.ddis.ddis_hr.menu.query.dto.MenuDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteMenuQueryServiceImpl implements FavoriteMenuQueryService {

    private final FavoriteMenuMapper favoriteMenuMapper;

    @Override
    public List<MenuDTO> getFavoriteMenus(Long employeeId) {
        return favoriteMenuMapper.selectFavoriteMenus(employeeId);
    }
}