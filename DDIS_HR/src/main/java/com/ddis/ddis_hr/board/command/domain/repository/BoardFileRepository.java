package com.ddis.ddis_hr.board.command.domain.repository;

import com.ddis.ddis_hr.board.command.domain.aggregate.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
    List<BoardFile> findByBoard_BoardId(Long boardId);
}

