package com.ddis.ddis_hr.dictionary.command.application.service;

import com.ddis.ddis_hr.dictionary.command.application.dto.DictionaryCreateDTO;
import com.ddis.ddis_hr.dictionary.command.application.dto.DictionaryUpdateDTO;

public interface DictionaryService {
    Long createDictionary(DictionaryCreateDTO dto);
    void updateDictionary(Long dictionaryId, DictionaryUpdateDTO dto);
    void deleteDictionary(Long dictionaryId);
}

