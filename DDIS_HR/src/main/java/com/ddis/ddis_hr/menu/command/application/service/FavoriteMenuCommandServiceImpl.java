package com.ddis.ddis_hr.menu.command.application.service;

import com.ddis.ddis_hr.menu.command.application.dto.FavoriteMenuCommandDTO;
import com.ddis.ddis_hr.menu.command.application.dto.FavoriteMenuOrderChangeDTO;
import com.ddis.ddis_hr.menu.command.domain.aggregate.FavoriteMenu;
import com.ddis.ddis_hr.menu.command.domain.repository.FavoriteMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteMenuCommandServiceImpl implements FavoriteMenuCommandService {

    private final FavoriteMenuRepository favoriteMenuRepository;

    @Override
    @Transactional
    public void addFavoriteMenu(FavoriteMenuCommandDTO dto) {
        FavoriteMenu favorite = FavoriteMenu.of(dto);
        favoriteMenuRepository.save(favorite);
    }

    @Override
    @Transactional
    public void removeFavoriteMenu(Long employeeId, Long menuId) {
        favoriteMenuRepository.deleteByEmployeeIdAndMenuId(employeeId, menuId);
    }

    @Override
    @Transactional
    public void changeFavoriteMenuOrder(FavoriteMenuOrderChangeDTO dto) {
        for (FavoriteMenuOrderChangeDTO.OrderItem item : dto.getOrders()) {
            favoriteMenuRepository
                    .findByEmployeeIdAndMenuId(dto.getEmployeeId(), item.getMenuId())
                    .ifPresent(fm -> {
                        fm.setDisplayOrder(item.getDisplayOrder());
                        // 변경 감지로 저장됨 (flush 생략 가능)
                    });
        }
    }

}
