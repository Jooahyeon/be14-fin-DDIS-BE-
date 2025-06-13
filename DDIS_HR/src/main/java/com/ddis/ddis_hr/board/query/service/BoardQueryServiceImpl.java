package com.ddis.ddis_hr.board.query.service;

import java.util.List;

import com.ddis.ddis_hr.board.query.dto.BoardFileDTO;
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
    public List<BoardListDTO> getBoardList() {
        return mapper.selectBoardList();
    }

    @Override
    public BoardDTO getBoardById(Long boardId) {
        BoardDTO dto = mapper.selectBoardById(boardId);
        if (dto != null) {
            List<BoardFileDTO> files = mapper.selectFilesByBoardId(boardId);
            dto.setFileList(files);
        }
        return dto;
    }
}

