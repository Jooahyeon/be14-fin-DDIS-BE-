package com.ddis.ddis_hr.dictionary.query.controller;

import com.ddis.ddis_hr.dictionary.query.dto.DictionaryQueryDTO;
import com.ddis.ddis_hr.dictionary.query.service.DictionaryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dictionary")
@RequiredArgsConstructor
public class DictionaryQueryController {

    private final DictionaryQueryService service;

    // 전체 용어사전 조회
    @GetMapping
    public List<DictionaryQueryDTO> getAllDictionary() {
        return service.getAllDictionary();
    }
}
