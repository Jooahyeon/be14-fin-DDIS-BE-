package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateCommandDTO;
import com.ddis.ddis_hr.eapproval.query.dto.ContentQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDetailResponseQueryDTO;
import com.ddis.ddis_hr.eapproval.query.mapper.DraftDocumentMapper;
import com.ddis.ddis_hr.eapproval.query.mapper.DraftMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DraftQueryServiceImpl implements DraftQueryService {

    private final DraftMapper draftMapper;
    private final ObjectMapper objectMapper;

    // 기안 상세조회
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

    // 회수
    @Override
    @Transactional
    public void recallDocument(Long docId) {
        log.info("▶▶ recallDocument() 시작: docId={}", docId);
        try {
            // 1) 첫 번째 결재 진행 여부 확인
            int count = draftMapper.countFirstApproverAction(docId);
            if (count > 0) {
                log.warn("회수 불가: 이미 첫 번째 결재가 완료됨, docId={}", docId);
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "첫 번째 결재가 완료되어 회수할 수 없습니다."
                );
            }
            // 2) document_box에서 기안자 제외한 결재자/협조자 삭제
            draftMapper.deleteDocumentBoxExceptDrafter(docId);
            log.debug("결재자 문서함 삭제 완료: docId={}", docId);
            // 3) draft_documents 상태를 '회수'로 변경
            draftMapper.updateDocumentStatusToRecalled(docId);
            log.info("문서 상태 회수로 업데이트 완료: docId={}", docId);
        } catch (ResponseStatusException e) {
            // 이미 지정된 HTTP 상태 예외 재throw
            throw e;
        } catch (Exception e) {
            // 예기치 못한 예외는 로그 후 HTTP 500으로 변환
            log.error("recallDocument 처리 중 예외 발생: docId={}", docId, e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "문서 회수 중 오류가 발생했습니다. 다시 시도해주세요.",
                    e
            );
        }
    }
}