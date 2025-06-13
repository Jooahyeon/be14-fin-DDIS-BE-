package com.ddis.ddis_hr.board.query.service;

import java.util.List;
import com.ddis.ddis_hr.board.query.dto.BoardDTO;
import com.ddis.ddis_hr.board.query.dto.BoardListDTO;

public interface BoardQueryService {
    BoardDTO             getBoardById(Long boardId);

    /** 목록 조회 */
    List<BoardListDTO>  getBoardList();
}