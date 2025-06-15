package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateCommandDTO;
import com.ddis.ddis_hr.eapproval.query.dto.ContentQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDetailResponseQueryDTO;
import com.ddis.ddis_hr.eapproval.query.mapper.DraftDocumentMapper;
import com.ddis.ddis_hr.eapproval.query.mapper.DraftMapper;
import com.ddis.ddis_hr.eapproval.query.mapper.FindDrafterMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DraftQueryServiceImpl implements DraftQueryService {

    private final FindDrafterMapper findDrafterMapper;


    private final DraftMapper draftMapper;
    private final ObjectMapper objectMapper;
    private final DraftDocumentMapper draftDocumentMapper;

//    @Override
//    public DraftDetailResponseQueryDTO getDraftDetail(Long docId) {
//        // ✅ 1. MyBatis를 통해 기본 문서 정보 + doc_content(JSON 문자열) 조회
//        DraftDetailResponseQueryDTO dto = draftMapper.selectDraftDetail(docId);
//
//        // 🔍 doc_content 확인 (콘솔 출력)
//        System.out.println("✔ docContent from DB: " + dto.getDocContent());
//
//        try {
//            // ✅ 2. doc_content가 존재하면 JSON → 객체로 파싱
//            if (dto.getDocContent() != null) {
//                ContentQueryDTO parsedContent = objectMapper.readValue(dto.getDocContent(), ContentQueryDTO.class);
//
//                // ✅ 3. contentDto 객체 설정 (본문, 파일, 제목 등 포함)
//                dto.setContentDto(parsedContent);
//
//                // ✅ 4. 수신자/참조자 필드는 문자열로 합쳐서 저장
//                if (parsedContent.getReceiver() != null) {
//                    dto.setReceiver(String.join(", ", parsedContent.getReceiver()));
//                }
//                if (parsedContent.getReference() != null) {
//                    dto.setReferer(String.join(", ", parsedContent.getReference()));
//                }
//
//            } else {
//                // ⚠️ null이면 경고 로그
//                System.out.println("⚠️ doc_content가 null입니다.");
//            }
//        } catch (Exception e) {
//            // ❌ 파싱 실패 시 예외 로그 출력
//            System.err.println("❌ doc_content 파싱 오류: " + e.getMessage());
//        }
//
//        // ✅ 5. 최종 결과 반환
//        return dto;
//    }

    @Override
    public DraftDetailResponseQueryDTO getDraftDetail(Long docId) {
        DraftDetailResponseQueryDTO dto = draftMapper.selectDraftDetail(docId);

        String rawContent = dto.getDocContent();

        System.out.println("✔ docContent from DB: " + rawContent);

        try {
            if (rawContent != null && rawContent.trim().startsWith("{")) {
                // ✅ JSON 형식인 경우 → 파싱 시도
                ContentQueryDTO parsed = objectMapper.readValue(rawContent, ContentQueryDTO.class);
                dto.setContentDto(parsed);

                if (parsed.getReceiver() != null)
                    dto.setReceiver(String.join(", ", parsed.getReceiver()));
                if (parsed.getReference() != null)
                    dto.setReferer(String.join(", ", parsed.getReference()));

            } else {
                // ✅ HTML 또는 단순 텍스트인 경우 → body 필드에 직접 할당
                ContentQueryDTO fallback = new ContentQueryDTO();
                fallback.setBody(rawContent);
                dto.setContentDto(fallback);
            }
        } catch (Exception e) {
            System.err.println("❌ doc_content 파싱 오류: " + e.getMessage());

            // 예외 발생 시에도 최소한 body 값은 보여지도록 fallback 처리
            ContentQueryDTO fallback = new ContentQueryDTO();
            fallback.setBody(rawContent);
            dto.setContentDto(fallback);
        }

        return dto;
    }


    @Override
    public Long createDraft(DraftCreateCommandDTO requestDto) {
        return 0L;
    }

    @Override
    public List<DraftDTO> getMyDrafts(Long employeeId) {

        return draftDocumentMapper.selectDraftsByDrafter(employeeId);
    }



}
