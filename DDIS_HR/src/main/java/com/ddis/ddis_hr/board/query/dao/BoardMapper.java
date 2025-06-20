package com.ddis.ddis_hr.board.query.dao;

import java.util.List;

import com.ddis.ddis_hr.board.query.dto.BoardDTO;
import com.ddis.ddis_hr.board.query.dto.BoardFileDTO;
import org.apache.ibatis.annotations.Mapper;
import com.ddis.ddis_hr.board.query.dto.BoardListDTO;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface BoardMapper {
    // 목록 조회
    List<BoardListDTO> selectBoardList();

    // 단일 공지 조회
    BoardDTO selectBoardById(@Param("boardId") Long boardId);

    // 단일 공지의 첨부 파일 전체 조회
    List<BoardFileDTO> selectFilesByBoardId(@Param("boardId") Long boardId);
}
