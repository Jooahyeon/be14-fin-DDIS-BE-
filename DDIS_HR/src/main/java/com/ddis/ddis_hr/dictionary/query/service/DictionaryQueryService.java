package com.ddis.ddis_hr.dictionary.query.service;

import com.ddis.ddis_hr.dictionary.query.dao.DictionaryMapper;
import com.ddis.ddis_hr.dictionary.query.dto.DictionaryQueryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DictionaryQueryService {

    private final DictionaryMapper dictionaryMapper;

    public List<DictionaryQueryDTO> getAllDictionary() {
        return dictionaryMapper.selectAllDictionary();
    }
}
