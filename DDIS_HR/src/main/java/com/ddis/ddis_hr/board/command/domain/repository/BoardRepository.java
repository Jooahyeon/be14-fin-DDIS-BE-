package com.ddis.ddis_hr.board.command.domain.repository;

import com.ddis.ddis_hr.board.command.domain.aggregate.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
