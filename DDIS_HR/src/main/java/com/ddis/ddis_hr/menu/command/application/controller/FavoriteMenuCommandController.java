package com.ddis.ddis_hr.menu.command.application.controller;

import com.ddis.ddis_hr.common.CommonResponse;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import com.ddis.ddis_hr.menu.command.application.dto.FavoriteMenuCommandDTO;
import com.ddis.ddis_hr.menu.command.application.dto.FavoriteMenuOrderChangeDTO;
import com.ddis.ddis_hr.menu.command.application.service.FavoriteMenuCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menus/favorites")
@RequiredArgsConstructor
public class FavoriteMenuCommandController {

    private final FavoriteMenuCommandService favoriteMenuCommandService;

    @PostMapping
    public ResponseEntity<?> addFavorite(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody FavoriteMenuCommandDTO dto) {

        dto.setEmployeeId(user.getEmployeeId());
        favoriteMenuCommandService.addFavoriteMenu(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success("자주 쓰는 메뉴가 등록되었습니다."));
    }

    @DeleteMapping
    public ResponseEntity<?> removeFavorite(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam Long menuId) {

        favoriteMenuCommandService.removeFavoriteMenu(user.getEmployeeId(), menuId);
        return ResponseEntity.ok(CommonResponse.success("자주 쓰는 메뉴가 삭제되었습니다."));
    }

    @PatchMapping("/order")
    public ResponseEntity<?> changeOrder(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody FavoriteMenuOrderChangeDTO dto) {

        dto.setEmployeeId(user.getEmployeeId());
        favoriteMenuCommandService.changeFavoriteMenuOrder(dto);
        return ResponseEntity.ok(CommonResponse.success("자주 쓰는 메뉴 순서가 변경되었습니다."));
    }

}
