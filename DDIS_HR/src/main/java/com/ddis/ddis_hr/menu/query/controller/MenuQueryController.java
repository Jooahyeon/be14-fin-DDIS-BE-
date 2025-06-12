package com.ddis.ddis_hr.menu.query.controller;

import com.ddis.ddis_hr.menu.query.dto.MenuDTO;
import com.ddis.ddis_hr.menu.query.service.MenuQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuQueryController {

    private final MenuQueryService menuQueryService;

    // 전체 메뉴 조회
    @GetMapping
    public ResponseEntity<List<MenuDTO>> getAllMenus() {
        List<MenuDTO> menus = menuQueryService.getAllMenus();
        return ResponseEntity.ok(menus); // 200 OK + body
    }
}
