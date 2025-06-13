package com.ddis.ddis_hr.board.query.service;

import java.util.List;
import com.ddis.ddis_hr.board.query.dto.BoardDTO;
import com.ddis.ddis_hr.board.query.dto.BoardListDTO;

public interface BoardQueryService {
    List<BoardListDTO> getBoardList();
    BoardDTO           getBoardById(Long boardId);
}