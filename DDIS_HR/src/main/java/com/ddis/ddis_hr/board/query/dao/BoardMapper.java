package com.ddis.ddis_hr.board.query.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ddis.ddis_hr.board.query.dto.BoardDTO;
import com.ddis.ddis_hr.board.query.dto.BoardListDTO;

@Mapper
public interface BoardMapper {

    /* 단일 공지사항 조회 */
    BoardDTO       selectBoardById(Long boardId);

    /** 목록 전용: 공지사항번호, 제목, 작성자, 작성일자 */
    List<BoardListDTO> selectBoardList();
}
