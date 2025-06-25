package com.ddis.ddis_hr.dictionary.command.application.service;

import com.ddis.ddis_hr.dictionary.command.application.dto.DictionaryCreateDTO;
import com.ddis.ddis_hr.dictionary.command.application.dto.DictionaryUpdateDTO;
import com.ddis.ddis_hr.dictionary.command.domain.aggregate.Dictionary;
import com.ddis.ddis_hr.dictionary.command.domain.repository.DictionaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DictionaryServiceImpl implements DictionaryService {
    private final DictionaryRepository repository;

    @Override
    public Long createDictionary(DictionaryCreateDTO dto) {
        Dictionary entity = new Dictionary();
        entity.setDictionaryName(dto.getDictionaryName());
        entity.setDictionaryContent(dto.getDictionaryContent());
        entity.setDictionaryType(calcInitial(dto.getDictionaryName()));
        repository.save(entity);
        return entity.getDictionaryId();
    }

    @Override
    public void updateDictionary(Long id, DictionaryUpdateDTO dto) {
        Dictionary entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 용어가 없습니다. id=" + id));

        entity.setDictionaryName(dto.getDictionaryName());
        entity.setDictionaryContent(dto.getDictionaryContent());
        entity.setDictionaryType(calcInitial(dto.getDictionaryName()));
        // 변경 감지로 자동 반영
    }

    /** 단어 첫 글자에서 “초성 or 영문 대문자” 를 뽑아 반환 */
    private String calcInitial(String word) {
        if (word == null || word.isEmpty()) {
            return "";
        }
        char ch = word.charAt(0);

        // 1) 영문(A–Z, a–z)은 대문자로
        if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
            return String.valueOf(Character.toUpperCase(ch));
        }

        // 2) 한글 음절이면 초성 계산
        int uni = ch - 0xAC00;
        if (uni >= 0 && uni <= 11171) {
            String[] CHO = {
                    "ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ","ㅅ",
                    "ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"
            };
            int idx = uni / (21 * 28);
            return CHO[idx];
        }

        // 3) 그 외 (숫자·특수문자 등)는 빈 문자열
        return "";
    }

    @Override
    public void deleteDictionary(Long dictionaryId) {
        repository.deleteById(dictionaryId);
    }
}

