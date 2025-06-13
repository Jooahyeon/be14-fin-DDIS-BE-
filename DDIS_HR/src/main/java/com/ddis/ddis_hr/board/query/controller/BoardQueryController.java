package com.ddis.ddis_hr.board.query.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ddis.ddis_hr.board.query.dto.BoardListDTO;
import com.ddis.ddis_hr.board.query.dto.BoardDTO;
import com.ddis.ddis_hr.board.query.service.BoardQueryService;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardQueryController {

    private final BoardQueryService service;

    // 목록 조회
    @GetMapping("/lists")
    public ResponseEntity<List<BoardListDTO>> list() {
        return ResponseEntity.ok(service.getBoardList());
    }

    // 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> detail(@PathVariable Long id) {
        BoardDTO dto = service.getBoardById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}

