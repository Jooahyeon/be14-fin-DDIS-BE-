package com.ddis.ddis_hr.dictionary.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dictionary")
@Getter
@Setter
@NoArgsConstructor
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dictionaryId;

    @Column(nullable = false, length = 100)
    private String dictionaryName;

    @Column(columnDefinition = "TEXT")
    private String dictionaryContent;

    @Column(length = 50)
    private String dictionaryType;
}
