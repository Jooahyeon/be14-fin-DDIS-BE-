package com.ddis.ddis_hr.menu.query.controller;

import com.ddis.ddis_hr.member.security.CustomUserDetails;
import com.ddis.ddis_hr.menu.query.dto.MenuDTO;
import com.ddis.ddis_hr.menu.query.service.FavoriteMenuQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus/favorites")
@RequiredArgsConstructor
public class FavoriteMenuQueryController {

    private final FavoriteMenuQueryService favoriteMenuQueryService;

    // 사원별 자주 쓰는 메뉴 조회

    // 로그인된 사용자의 자주 쓰는 메뉴 조회
    @GetMapping("/me")
    public ResponseEntity<List<MenuDTO>> getMyFavoriteMenus(
            @AuthenticationPrincipal CustomUserDetails user) {

        List<MenuDTO> favorites = favoriteMenuQueryService.getFavoriteMenus(user.getEmployeeId());
        return ResponseEntity.ok(favorites);
    }
}