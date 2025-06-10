package com.ddis.ddis_hr.menu.command.application.service;

import com.ddis.ddis_hr.menu.command.application.dto.FavoriteMenuCommandDTO;
import com.ddis.ddis_hr.menu.command.application.dto.FavoriteMenuOrderChangeDTO;

public interface FavoriteMenuCommandService {
    void addFavoriteMenu(FavoriteMenuCommandDTO dto);

    void removeFavoriteMenu(Long employeeId, Long menuId);

    void changeFavoriteMenuOrder(FavoriteMenuOrderChangeDTO dto);
}
