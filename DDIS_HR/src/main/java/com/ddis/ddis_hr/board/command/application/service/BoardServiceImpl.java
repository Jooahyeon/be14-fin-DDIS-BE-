package com.ddis.ddis_hr.board.command.application.service;

import com.ddis.ddis_hr.S3Config.service.S3Service;
import com.ddis.ddis_hr.board.command.application.dto.BoardCreateDTO;
import com.ddis.ddis_hr.board.command.application.dto.BoardUpdateDTO;
import com.ddis.ddis_hr.board.command.domain.aggregate.Board;
import com.ddis.ddis_hr.board.command.domain.aggregate.BoardFile;
import com.ddis.ddis_hr.board.command.domain.repository.BoardFileRepository;
import com.ddis.ddis_hr.board.command.domain.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@Transactional
public class BoardServiceImpl implements BoardService {
    private final BoardRepository      boardRepository;
    private final BoardFileRepository  boardFileRepository;
    private final S3Service            s3Service;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository,
                            BoardFileRepository boardFileRepository,
                            S3Service s3Service
    ){
        this.boardRepository = boardRepository;
        this.boardFileRepository = boardFileRepository;
        this.s3Service = s3Service;
    }

    @Override
    public Long createBoard(BoardCreateDTO dto, Long employeeId) {
        Board board = new Board();
        board.setBoardTitle(dto.getBoardTitle());
        board.setBoardContent(dto.getBoardContent());
        board.setEmployeeId(employeeId);
        boardRepository.save(board);

        if (dto.getFiles() != null) {
            dto.getFiles().forEach(f -> {
                BoardFile bf = new BoardFile();
                bf.setFileName(f.getFileName());
                bf.setFileUrl(f.getKey());
                bf.setFileSize(f.getFileSize());
                board.addFile(bf);
            });

            boardRepository.save(board);
        }
        return board.getBoardId();
    }

    @Override
    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Board not found"));
    }

    @Override
    @Transactional
    public void updateBoard(Long boardId, BoardUpdateDTO dto) {
        Board board = getBoard(boardId);
        board.setBoardTitle(dto.getBoardTitle());
        board.setBoardContent(dto.getBoardContent());

        Map<String, BoardFile> existing = boardFileRepository.findByBoard_BoardId(boardId)
                .stream().collect(Collectors.toMap(BoardFile::getFileUrl, bf -> bf));

        List<BoardFile> toDelete = existing.values().stream()
                .filter(bf -> dto.getFiles().stream()
                        .noneMatch(f -> f.getKey().equals(bf.getFileUrl())))
                .collect(Collectors.toList());
        toDelete.forEach(bf -> {
            s3Service.deleteFile(bf.getFileUrl(), null);
            board.getFiles().remove(bf);
        });

        dto.getFiles().stream()
                .filter(f -> !existing.containsKey(f.getKey()))
                .forEach(f -> {
                    BoardFile bf = new BoardFile();
                    bf.setFileName(f.getFileName());
                    bf.setFileUrl(f.getKey());
                    bf.setFileSize(f.getFileSize());
                    board.addFile(bf);
                });

        boardRepository.save(board);
    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = getBoard(boardId);

        board.getFiles().forEach(f -> s3Service.deleteFile(f.getFileUrl(), null));
        boardRepository.delete(board);
    }
}