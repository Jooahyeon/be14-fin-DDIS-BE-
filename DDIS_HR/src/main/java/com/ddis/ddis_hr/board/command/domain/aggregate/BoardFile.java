package com.ddis.ddis_hr.board.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "board_file")
@Getter @Setter @NoArgsConstructor
public class BoardFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    private String fileName;
    private String fileUrl;     // S3 key
    private Long fileSize;

    @Column(updatable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();
}
