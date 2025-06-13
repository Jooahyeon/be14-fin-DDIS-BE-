package com.ddis.ddis_hr.dictionary.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DictionaryQueryDTO {
    private Long dictionaryId;
    private String dictionaryName;
    private String dictionaryContent;
    private String dictionaryType;
}
