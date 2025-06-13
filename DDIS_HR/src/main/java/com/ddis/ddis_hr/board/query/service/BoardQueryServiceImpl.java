package com.ddis.ddis_hr.board.query.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ddis.ddis_hr.board.query.dao.BoardMapper;
import com.ddis.ddis_hr.board.query.dto.BoardDTO;
import com.ddis.ddis_hr.board.query.dto.BoardListDTO;

@Service
@Transactional(readOnly = true)
public class BoardQueryServiceImpl implements BoardQueryService {

    private final BoardMapper mapper;

    public BoardQueryServiceImpl(BoardMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BoardDTO getBoardById(Long boardId) {
        return mapper.selectBoardById(boardId);
    }

    @Override
    public List<BoardListDTO> getBoardList() {
        return mapper.selectBoardList();
    }
}
