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
        // 중복 등록 방지
        if (favoriteMenuRepository.existsByEmployeeIdAndMenuId(dto.getEmployeeId(), dto.getMenuId())) {
            throw new IllegalStateException("이미 등록된 즐겨찾기 메뉴입니다.");
        }

        // displayOrder 기본값 지정
        if (dto.getDisplayOrder() == null) {
            Long maxOrder = favoriteMenuRepository
                    .findMaxDisplayOrderByEmployeeId(dto.getEmployeeId())
                    .orElse(0L);
            dto.setDisplayOrder(maxOrder + 1);
        }

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
                    .ifPresentOrElse(fm -> {
                        fm.setDisplayOrder(item.getDisplayOrder());
                    }, () -> {
                        // 없는 경우 로깅 (무시 or throw는 선택)
                        System.out.printf("순서 변경 대상 없음: employeeId=%d, menuId=%d%n",
                                dto.getEmployeeId(), item.getMenuId());
                    });
        }
    }
}
