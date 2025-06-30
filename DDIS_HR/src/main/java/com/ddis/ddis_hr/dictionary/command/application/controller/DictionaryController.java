package com.ddis.ddis_hr.dictionary.command.application.controller;

import com.ddis.ddis_hr.dictionary.command.application.dto.DictionaryCreateDTO;
import com.ddis.ddis_hr.dictionary.command.application.dto.DictionaryUpdateDTO;
import com.ddis.ddis_hr.dictionary.command.application.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dictionary")
@RequiredArgsConstructor
public class DictionaryController {

    private final DictionaryService service;

    // 생성
    @PostMapping
    @PreAuthorize("hasAnyRole('HR')")
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody DictionaryCreateDTO dto) {
        return service.createDictionary(dto);
    }

    // 수정
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @PathVariable("id") Long id,
            @RequestBody DictionaryUpdateDTO dto
    ) {
        service.updateDictionary(id, dto);
    }

    // 삭제
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        service.deleteDictionary(id);
    }
}
