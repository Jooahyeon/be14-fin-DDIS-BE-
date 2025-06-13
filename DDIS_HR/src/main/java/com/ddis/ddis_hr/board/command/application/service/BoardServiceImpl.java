package com.ddis.ddis_hr.board.command.application.service;

import com.ddis.ddis_hr.S3Config.service.S3Service;
import com.ddis.ddis_hr.board.command.application.dto.BoardCreateDTO;
import com.ddis.ddis_hr.board.command.application.dto.BoardUpdateDTO;
import com.ddis.ddis_hr.board.command.domain.aggregate.Board;
import com.ddis.ddis_hr.board.command.domain.aggregate.BoardFile;
import com.ddis.ddis_hr.board.command.domain.repository.BoardFileRepository;
import com.ddis.ddis_hr.board.command.domain.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository      boardRepo;
    private final BoardFileRepository  fileRepo;
    private final S3Service s3Service;    // 이미 구성된 S3Service

    @Override
    @Transactional
    public Long createBoard(BoardCreateDTO dto, Long employeeId) {
        Board board = new Board();
        board.setBoardTitle(dto.getBoardTitle());
        board.setBoardContent(dto.getBoardContent());
        board.setEmployeeId(employeeId);
        boardRepo.save(board);

        if (dto.getFiles() != null) {
            dto.getFiles().forEach(f -> {
                BoardFile bf = new BoardFile();
                bf.setFileName(f.getFileName());
                bf.setFileUrl(f.getKey());
                bf.setFileSize(f.getFileSize());
                board.addFile(bf);
            });
            // cascade = ALL 이므로 board만 save 해도 파일까지 저장
            boardRepo.save(board);
        }
        return board.getBoardId();
    }

    @Override
    public Board getBoard(Long boardId) {
        return boardRepo.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Board not found"));
    }

    @Override
    @Transactional
    public void updateBoard(Long boardId, BoardUpdateDTO dto) {
        Board board = getBoard(boardId);
        board.setBoardTitle(dto.getBoardTitle());
        board.setBoardContent(dto.getBoardContent());

        // 기존 파일 맵
        Map<String, BoardFile> existing = fileRepo.findByBoard_BoardId(boardId)
                .stream().collect(Collectors.toMap(BoardFile::getFileUrl, bf -> bf));

        // 삭제할 것
        List<BoardFile> toDelete = existing.values().stream()
                .filter(bf -> dto.getFiles().stream()
                        .noneMatch(f -> f.getKey().equals(bf.getFileUrl())))
                .collect(Collectors.toList());
        toDelete.forEach(bf -> {
            s3Service.deleteFile(bf.getFileUrl(), null);
            board.getFiles().remove(bf);
        });

        // 추가할 것
        dto.getFiles().stream()
                .filter(f -> !existing.containsKey(f.getKey()))
                .forEach(f -> {
                    BoardFile bf = new BoardFile();
                    bf.setFileName(f.getFileName());
                    bf.setFileUrl(f.getKey());
                    bf.setFileSize(f.getFileSize());
                    board.addFile(bf);
                });

        boardRepo.save(board);
    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = getBoard(boardId);
        // S3 삭제
        board.getFiles().forEach(f -> s3Service.deleteFile(f.getFileUrl(), null));
        boardRepo.delete(board);
    }
}