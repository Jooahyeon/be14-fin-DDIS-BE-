package com.ddis.ddis_hr.board.command.application.controller;

import com.ddis.ddis_hr.board.command.application.dto.BoardCreateDTO;
import com.ddis.ddis_hr.board.command.application.dto.BoardUpdateDTO;
import com.ddis.ddis_hr.board.command.application.service.BoardService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Long> create(
            @RequestBody BoardCreateDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long id = boardService.createBoard(dto, user.getEmployeeId());
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<Void> update(
            @PathVariable Long boardId,
            @RequestBody BoardUpdateDTO dto
    ) {
        boardService.updateBoard(boardId, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> delete(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }
}
