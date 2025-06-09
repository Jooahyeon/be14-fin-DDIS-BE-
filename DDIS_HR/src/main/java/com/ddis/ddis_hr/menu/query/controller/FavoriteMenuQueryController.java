package com.ddis.ddis_hr.menu.query.controller;

import com.ddis.ddis_hr.menu.query.dto.MenuDTO;
import com.ddis.ddis_hr.menu.query.service.FavoriteMenuQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus/favorites")
@RequiredArgsConstructor
public class FavoriteMenuQueryController {

    private final FavoriteMenuQueryService favoriteMenuQueryService;

    // 사원별 자주찾는 메뉴 조회
    @GetMapping("/{employeeId}")
    public ResponseEntity<List<MenuDTO>> getFavoriteMenus(@PathVariable Long employeeId) {
        List<MenuDTO> favorites = favoriteMenuQueryService.getFavoriteMenus(employeeId);
        return ResponseEntity.ok(favorites);
    }
}