package com.ddis.ddis_hr.menu.query.service;

import com.ddis.ddis_hr.menu.query.dao.MenuMapper;
import com.ddis.ddis_hr.menu.query.dto.MenuDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuQueryServiceImpl implements MenuQueryService {

    private final MenuMapper menuMapper;

    @Override
    public List<MenuDTO> getAllMenus() {
        return menuMapper.selectAllMenus();
    }
}
