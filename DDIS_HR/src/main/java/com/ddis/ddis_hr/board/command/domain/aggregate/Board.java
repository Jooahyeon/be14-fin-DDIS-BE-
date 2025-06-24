package com.ddis.ddis_hr.board.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@Setter
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private String boardTitle;
    private String boardContent;

    @Column(updatable = false)
    private LocalDateTime boardCreatedAt = LocalDateTime.now();

    private Long employeeId;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardFile> files = new ArrayList<>();

    // helper
    public void addFile(BoardFile file) {
        files.add(file);
        file.setBoard(this);
    }
}
