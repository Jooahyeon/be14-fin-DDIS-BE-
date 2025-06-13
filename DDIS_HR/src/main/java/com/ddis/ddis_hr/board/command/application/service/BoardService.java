package com.ddis.ddis_hr.board.command.application.service;


import com.ddis.ddis_hr.board.command.application.dto.BoardCreateDTO;
import com.ddis.ddis_hr.board.command.application.dto.BoardUpdateDTO;
import com.ddis.ddis_hr.board.command.domain.aggregate.Board;

public interface BoardService  {
    Long createBoard(BoardCreateDTO dto, Long employeeId);
    Board getBoard(Long boardId);
    void    updateBoard(Long boardId, BoardUpdateDTO dto);
    void    deleteBoard(Long boardId);
}