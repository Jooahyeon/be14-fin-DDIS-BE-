package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.S3Config.service.S3Service;
import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateCommandDTO;
import com.ddis.ddis_hr.eapproval.query.dto.ContentQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDetailResponseQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.FileQueryDTO;
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
    private final S3Service s3Service;
    private final DraftDocumentMapper draftDocumentMapper;

    // 기안 상세조회
    @Override
    public DraftDetailResponseQueryDTO getDraftDetail(Long docId) {
        // 1) MyBatis 로 DTO + contentDto.refFile (SQL 매핑) 까지 한 번에 가져옴
        DraftDetailResponseQueryDTO dto = draftMapper.selectDraftDetail(docId);

        // 2) rawContent(JSON 또는 HTML) 꺼내기
        String raw = dto.getDocContent();

        // 3) SQL 매핑으로 이미 채워진 contentDto 객체 얻기
        ContentQueryDTO content = dto.getContentDto();
        if (content == null) {
            content = new ContentQueryDTO();
            dto.setContentDto(content);
        }

        // 4) JSON 본문(body)만 덮어쓰기
        if (raw != null && raw.trim().startsWith("{")) {
            try {
                ContentQueryDTO parsedJson = objectMapper.readValue(raw, ContentQueryDTO.class);
                content.setBody(parsedJson.getBody());
                // (필요하면 parsedJson.getTitle(), receiver/reference 도 덮어쓰세요)
            } catch (Exception e) {
                log.warn("doc_content JSON 파싱 실패, 본문만 fallback", e);
                content.setBody(raw);
            }
        } else {
            content.setBody(raw);
        }

        // 5) S3 presigned URL 채우기
        if (content.getRefFile() != null) {
            for (FileQueryDTO f : content.getRefFile()) {
                String url = s3Service.generateDownloadUrl(f.getKey(), f.getType());
                f.setUrl(url);
            }
        }

        // 6) 수신자·참조자 문자열 조립
        if (content.getReceiver()  != null) dto.setReceiver(String.join(", ", content.getReceiver()));
        if (content.getReference() != null) dto.setReferer(String.join(", ", content.getReference()));

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
    @Override
    public Long createDraft(DraftCreateCommandDTO requestDto) {
        return 0L;
    }

    @Override
    public List<DraftDTO> getMyDrafts(Long employeeId) {

        return draftDocumentMapper.selectDraftsByDrafter(employeeId);
    }


    @Override
    public List<DraftDTO> getMyReference(Long employeeId) {
        return draftDocumentMapper.selectReferenceByemp(employeeId);

    }


}