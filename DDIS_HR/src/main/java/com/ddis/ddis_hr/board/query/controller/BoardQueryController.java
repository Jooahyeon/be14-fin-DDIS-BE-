package com.ddis.ddis_hr.board.query.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ddis.ddis_hr.board.query.dto.BoardListDTO;
import com.ddis.ddis_hr.board.query.dto.BoardDTO;
import com.ddis.ddis_hr.board.query.service.BoardQueryService;

@RestController
@RequestMapping("/boards")
public class BoardQueryController {

    private final BoardQueryService service;

    public BoardQueryController(BoardQueryService service) {
        this.service = service;
    }

    /** 1) 공지사항 목록 조회 */
    @GetMapping("/lists")
    public ResponseEntity<List<BoardListDTO>> listBoard() {
        return ResponseEntity.ok(service.getBoardList());
    }

    /** 2) 단일 공지사항 조회 *Optional* */
    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoard(@PathVariable Long id) {
        BoardDTO dto = service.getBoardById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}

