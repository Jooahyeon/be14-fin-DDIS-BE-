package com.ddis.ddis_hr.board.command.application.controller;

import com.ddis.ddis_hr.board.command.application.dto.BoardCreateDTO;
import com.ddis.ddis_hr.board.command.application.dto.BoardUpdateDTO;
import com.ddis.ddis_hr.board.command.application.service.BoardService;
import com.ddis.ddis_hr.member.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 공지사항 등록
    @PostMapping
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<Long> create(
            @RequestBody BoardCreateDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long id = boardService.createBoard(dto, user.getEmployeeId());
        return ResponseEntity.ok(id);
    }

    // 공지사항 수정
    @PutMapping("/{boardId}")
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<Void> update(
            @PathVariable Long boardId,
            @RequestBody BoardUpdateDTO dto
    ) {
        boardService.updateBoard(boardId, dto);
        return ResponseEntity.noContent().build();
    }

    // 공지사항 삭제
    @DeleteMapping("/{boardId}")
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<Void> delete(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }
}
