package com.ddis.ddis_hr.dictionary.query.dao;

import com.ddis.ddis_hr.dictionary.query.dto.DictionaryQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DictionaryMapper {

    // 전체 조회
    List<DictionaryQueryDTO> selectAllDictionary();
}
